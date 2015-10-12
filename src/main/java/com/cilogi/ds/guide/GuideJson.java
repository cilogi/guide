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

import com.cilogi.ds.guide.compile.CompileInfo;
import com.cilogi.ds.guide.diagrams.Diagrams;
import com.cilogi.ds.guide.mapper.GuideMapper;
import com.cilogi.ds.guide.galleries.Gallery;
import com.cilogi.ds.guide.listings.Listing;
import com.cilogi.ds.guide.media.GuideAudio;
import com.cilogi.ds.guide.media.GuideImage;
import com.cilogi.ds.guide.pages.Page;
import com.cilogi.ds.guide.shop.Shop;
import com.cilogi.ds.guide.tours.Tour;
import com.cilogi.util.path.PathUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.google.common.collect.Sets;
import lombok.*;

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

    @Getter
    private java.lang.String name;

    @Getter
    private String guideSpecVersion;

    @Getter @Setter
    private Config config;

    private String title;

    @Getter @Setter
    private java.lang.String owner;

    @Getter @Setter
    private String servingVersion;

    @Getter @Setter
    private java.util.Set<String> versions;

    @Getter @Setter
    private List<Page> pages;

    @Getter @Setter
    private Map<String,String> pageDigests;

    @Getter @Setter
    private Diagrams diagrams;

    @Getter @Setter
    private Set<GuideImage> images;

    @Getter @Setter
    private Set<GuideAudio> audioClips;

    @Getter @Setter
    private List<Tour> tours;

    @Getter @Setter
    private List<Gallery> galleries;

    @Getter @Setter
    private List<Listing> listings;

    @Getter @Setter
    private Map<String,byte[]> etags;

    @Getter @Setter
    private Shop shop;

    @Getter @Setter
    private int sequenceIndex;

    @Getter @Setter
    private CompileInfo compileInfo;

    public static GuideJson fromJSON(String data) throws IOException {
        GuideMapper mapper = new GuideMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper.readValue(data, GuideJson.class);
    }

    public  String getConfigName() {
        return CONFIG_NAME;
    }

    public GuideJson() {
        title = "";
        guideSpecVersion = DEFAULT_GUIDE_SPEC_VERSION;
        versions = Sets.newHashSet(DEFAULT_VERSION);
        servingVersion = DEFAULT_VERSION;
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
        sequenceIndex = 1;
    }

    public GuideJson(@NonNull String name, @NonNull String owner) {
        this();
        this.name = name;
        setTitle(name);
        this.owner = owner;
    }

    /**
     * Copy constructor
     * @param guide The guide to copy
     */
    public GuideJson(IGuide guide) {
        this.guideSpecVersion = guide.getGuideSpecVersion();
        this.name = guide.getName();
        this.title = guide.getTitle();
        this.owner = guide.getOwner();
        this.servingVersion = guide.getServingVersion();
        this.versions = Sets.newHashSet(guide.getVersions());
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
        this.sequenceIndex = guide.getSequenceIndex();
        this.compileInfo = (guide.getCompileInfo() == null) ? null : new CompileInfo(guide.getCompileInfo());
    }

    /**
     * Can this guide be saved properly?
     * @return true iff its OK to save
     */
    @JsonIgnore
    public boolean isValidState() {
        return name.length() > 0 && owner != null;
    }

    @JsonIgnore
    public boolean isDeployed() {
        return servingVersion != null;
    }

    public String getTitle() {
        return (title == null) ? name : title;
    }

    public void setTitle(@NonNull String name) {
        title = name;
        getConfig().setTitle(name);
    }

    public GuideJson safe() {
        return new GuideJson(this);
    }

    public String toJSONString() {
       return toJSONString(false);
    }

    public Page findPage(int pageId) {
        for (Page page: pages) {
            if (page.getId() == pageId) {
                return page;
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

    public synchronized boolean setImageDigest(String imageName, int width, int height, String digest) {
        String imageId = PathUtil.name(imageName);
        Set<GuideImage> images = getImages();
        for (GuideImage image : images) {
            if (imageId.equals(image.getId())) {
                image.setDigest(digest)
                     .setWidth(width)
                     .setHeight(height)
                     .setUrl(imageName);
                return true;
            }
        }
        return false;
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

    public String toJSONString(boolean isSafe) {
        try {
            GuideMapper mapper = new GuideMapper();
            return mapper.writeValueAsString(isSafe? safe() : this);
        } catch (JsonProcessingException e) {
            LOG.severe("Can't convert Guide " + this + " to JSON string");
            return "{}";
        }
    }
}
