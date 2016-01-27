// Copyright (c) 2011 Tim Niblett All Rights Reserved.
//
// File:        Guide.java  (20-Oct-2011)
// Author:      tim
// $Id$
//
// Copyright in the whole and every part of this source file belongs to
// Tim Niblett (the Author) and may not be used,
// sold, licenced, transferred, copied or reproduced in whole or in
// part in any manner or form or in or on any media to any person
// other than in accordance with the terms of The Author's agreement
// or otherwise without the prior written consent of The Author.  All
// information contained in this source file is confidential information
// belonging to The Author and as such may not be disclosed other
// than in accordance with the terms of The Author's agreement, or
// otherwise, without the prior written consent of The Author.  As
// confidential information this source file must be kept fully and
// effectively secure at all times.
//

package com.cilogi.ds.guide;

import com.cilogi.ds.guide.diagrams.Diagrams;
import com.cilogi.ds.guide.mapper.GuideMapper;
import com.cilogi.ds.guide.galleries.Gallery;
import com.cilogi.ds.guide.listings.Listing;
import com.cilogi.ds.guide.mapper.Location;
import com.cilogi.ds.guide.media.GuideAudio;
import com.cilogi.ds.guide.media.GuideImage;
import com.cilogi.ds.guide.pages.Page;
import com.cilogi.ds.guide.pages.PageImage;
import com.cilogi.ds.guide.shop.Shop;
import com.cilogi.ds.guide.tours.PageRef;
import com.cilogi.ds.guide.tours.Tour;
import com.cilogi.ds.guide.tours.TourStop;
import com.cilogi.util.path.PathUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import lombok.*;
import org.hjson.JsonValue;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;


@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
@ToString
@Data
public class GuideJson implements Serializable, IGuide {
    @SuppressWarnings("unused")
    static final Logger LOG = Logger.getLogger(GuideJson.class.getName());
    private static final long serialVersionUID = -9153256781053121634L;

    private static final String CONFIG_NAME = "config.json";
    private static final String DEFAULT_VERSION = "1";
    private static final String DEFAULT_GUIDE_SPEC_VERSION = "3";

    private static final Set<String> LOCAL_GUIDES = ImmutableSet.of(
            "stop"
    );

    private java.lang.String name;

    private final String guideSpecVersion;

    private Config config;

    private String title;

    private String description;

    private List<Page> pages;

    private Map<String,String> pageDigests;

    private Diagrams diagrams;

    private Set<GuideImage> images;

    private Set<GuideAudio> audioClips;

    private List<Tour> tours;

    private List<Gallery> galleries;

    private List<Listing> listings;

    private Map<String,byte[]> etags;

    private Shop shop;

    public static Set<String> localGuides() {
        return LOCAL_GUIDES;
    }

    public static GuideJson fromJSON(String data) throws IOException {
        GuideMapper mapper = new GuideMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper.readValue(JsonValue.readHjson(data).toString(), GuideJson.class);
    }

    public  String getConfigName() {
        return CONFIG_NAME;
    }

    public GuideJson() {
        title = "";
        description = "";
        guideSpecVersion = DEFAULT_GUIDE_SPEC_VERSION;
        config = new Config();
        pages = new ArrayList<>();
        pageDigests = new ConcurrentHashMap<>();
        diagrams = new Diagrams();
        images = Sets.newConcurrentHashSet();
        audioClips = Sets.newConcurrentHashSet();
        tours = new ArrayList<>();
        galleries = new ArrayList<>();
        listings = new ArrayList<>();
        etags = new java.util.HashMap<>();
    }

    public GuideJson(@NonNull String name) {
        this();
        this.name = name;
        setTitle(name);
    }

    /**
     * Copy constructor
     * @param guide The guide to copy
     */
    public GuideJson(IGuide guide) {
        this.guideSpecVersion = guide.getGuideSpecVersion();
        this.name = guide.getName();
        this.title = guide.getTitle();
        this.description = guide.getDescription();
        this.pages = new ArrayList<>(guide.getPages());
        this.pageDigests = new ConcurrentHashMap<>(guide.getPageDigests());
        this.diagrams = new Diagrams(guide.getDiagrams());
        this.images = Sets.newConcurrentHashSet(guide.getImages());
        this.audioClips = Sets.newConcurrentHashSet(guide.getAudioClips());
        this.tours = new ArrayList<>(guide.getTours());
        this.galleries = new ArrayList<>(guide.getGalleries());
        this.listings = new ArrayList<>(guide.getListings());
        this.etags = new java.util.HashMap<>(guide.getEtags());
        this.config = (guide.getConfig() == null) ? null : new Config(guide.getConfig());

        if (this.config != null && this.title != null) {
            this.config.setTitle(this.title);
        }

        this.shop = (guide.getShop() == null) ? null : new Shop(guide.getShop());
    }

    /**
     * Can this guide be saved properly?
     * @return true iff its OK to save
     */
    @JsonIgnore
    public boolean isValidState() {
        return name.length() > 0;
    }

    @JsonIgnore
    public boolean isShared() {
        return config != null && config.isShared();
    }

    public String getTitle() {
        return (title == null) ? name : title;
    }

    public void setTitle(@NonNull String name) {
        title = name;
        getConfig().setTitle(name);
    }

    public Set<String> tourNames() {
        List<Tour> tours = getTours();
        Set<String> names = new HashSet<>();
        for (Tour tour : tours) {
            names.add(tour.getId());
        }
        return names;
    }

    /**
     * Export named tour
     * @param name  The name of the tour
     * @return  null if there is no tour of that name, else the tour with stops prefixed with the guide
     * name, so that the tour can be imported without change into other guides.
     */
    public Tour exportTour(@NonNull String name) {
        Tour tour = findTour(name);
        if (tour == null) {
            return null;
        } else {
            Tour out = new Tour(tour);
            for (TourStop stop : out.getStops()) {
                PageRef pageRef = stop.getPageRef();
                if (!pageRef.isExternal()) {
                    stop.setPageRef(new PageRef(getName(), pageRef.getPageIndex()));
                }
            }
            return out;
        }
    }

    /**
     * Import tour into guide
     * @param tourToImport The tour
     * @return  The imported tour, where stops that are in this guide get converted to be local
     */
    public Tour importTour(@NonNull Tour tourToImport) {
        Tour tour = new Tour(tourToImport);

        Tour current = findTour(tour.getId());
        if (current != null) {
            getTours().remove(current);
        }

        String guideName = getName();
        for (TourStop stop : tour.getStops()) {
            PageRef ref = stop.getPageRef();
            if (ref.isExternal() && guideName.equals(ref.getGuideName())) {
                stop.setPageRef(new PageRef("", ref.getPageIndex()));
            }
        }
        getTours().add(tour);
        return tour;
    }

    public Tour findTour(@NonNull String name) {
        for (Tour tour : tours) {
            if (name.equals(tour.getId())) {
                return tour;
            }
        }
        return null;
    }

    public Page findPage(int pageId) {
        for (Page page: pages) {
            if (page.getId() == pageId) {
                return page;
            }
        }
        return null;
    }

    public GuideImage findImage(@NonNull String name) {
        Set<GuideImage> images = getImages();
        for (GuideImage image : images) {
            if (name.equals(image.getId())) {
                return image;
            }
        }
        return null;
    }

    public void updatePage(@NonNull Page page) {
        int id = page.getId();
        Page already = findPage(id);
        if (already != null) {
           pages.remove(already);
        }
        pages.add(page);
    }

    public void setPageDigest(int pageId, @NonNull String digest) {
        getPageDigests().put(Integer.toString(pageId), digest);
    }

    public synchronized GuideImage guideImageFor(String imageName) {
        String imageId = PathUtil.name(imageName);
        Set<GuideImage> images = getImages();
        for (GuideImage image : images) {
            if (imageId.equals(image.getId())) {
                return image;
            }
        }
        return null;
    }

    public synchronized GuideAudio guideAudioFor(String audioName) {
        String audioId = PathUtil.name(audioName);
        Set<GuideAudio> audioClips = getAudioClips();
        for (GuideAudio audioClip : audioClips) {
            if (audioId.equals(audioClip.getId())) {
                return audioClip;
            }
        }
        return null;
    }

    public synchronized void setGuideImageDigest(GuideImage guideImage, int width, int height, String digest) {
        guideImage.setDigest(digest)
                  .setWidth(width)
                  .setHeight(height);
    }

    public synchronized boolean setAudioDigest(String audioName, String digest) {
        String audioId = PathUtil.name(audioName);
        Set<GuideAudio> audios = getAudioClips();
        for (GuideAudio audio : audios) {
            if (audioId.equals(audio.getId())) {
                audio.setDigest(digest)
                     .setUrl(audioName);
                return true;
            }
        }
        return false;
    }

    public synchronized void appendPage(@NonNull Page page) {
        pages.add(page);
    }

    /**
     * If a page image has some alt text and the GuideImage does not have
     * a title set then set the title from the alt text.
     */
    public synchronized void synchronizeGuideImagesWithPageImages() {
        for (Page page : getPages()) {
            List<PageImage> images = page.getImages();
            for (PageImage image : images) {
                String src = PathUtil.name(image.getSrc());
                String alt = image.getAlt();

                GuideImage guideImage = findImage(src);
                if (guideImage == null) {
                    LOG.warning("Can't find image " + src);
                    continue;
                }
                String existingTitle = guideImage.getTitle();
                if (alt != null) {
                    if (existingTitle == null || existingTitle.trim().equals("")) {
                        guideImage.setTitle(alt);
                    }
                } else {
                    assert alt == null;
                    image.setAlt(existingTitle);
                }
            }
        }
    }

    public synchronized void setTourLocations() {
        for (Tour tour : getTours()) {
            if (tour.getLocation() == null) {
                for (TourStop stop : tour.getStops()) {
                    PageRef ref = stop.getPageRef();
                    if (ref.isCompatibleGuide(getName())) {
                        Page page = findPage(ref.getPageIndex());
                        if (page != null && page.getLocation() != null && page.getLocation().isLatLng()) {
                            tour.setLocation(page.getLocation());
                            break;
                        }
                    }
                }
                if (tour.getLocation() == null && getConfig().getLatlng() != null) {
                    tour.setLocation(new Location(getConfig().getLatlng()));
                }
            }
        }
    }

    public void makeToursPublic() {
        String guideName = getName();
        for (Tour tour : getTours()) {
            tour.makePublic(guideName);
        }
    }

    public String toJSONString() {
        try {
            GuideMapper mapper = new GuideMapper();
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            LOG.severe("Can't convert Guide " + this + " to JSON string");
            return "{}";
        }
    }
}
