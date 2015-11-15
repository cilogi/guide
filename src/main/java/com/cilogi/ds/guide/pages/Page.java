// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        Page.java  (23/01/15)
// Author:      tim
//
// Copyright in the whole and every part of this source file belongs to
// Cilogi (the Author) and may not be used, sold, licenced, 
// transferred, copied or reproduced in whole or in part in 
// any manner or form or in or on any media to any person other than 
// in accordance with the terms of The Author's agreement
// or otherwise without the prior written consent of The Author.  All
// information contained in this source file is confidential information
// belonging to The Author and as such may not be disclosed other
// than in accordance with the terms of The Author's agreement, or
// otherwise, without the prior written consent of The Author.  As
// confidential information this source file must be kept fully and
// effectively secure at all times.
//


package com.cilogi.ds.guide.pages;

import com.cilogi.ds.guide.mapper.GuideMapper;
import com.cilogi.ds.guide.mapper.LatLng;
import com.cilogi.ds.guide.mapper.Location;
import com.cilogi.ds.guide.tours.PageRef;
import com.cilogi.util.Digest;
import com.cilogi.util.MetaUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import lombok.Data;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@Data
public class Page implements Serializable, Comparable<Page> {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(Page.class);
    private static final long serialVersionUID = 1396319511865459378L;

    private int id;
    private String title;
    private String guideName;
    private List<PageImage> images;
    private String url;
    private Location location;
    private List<PageLink> pageLinks;
    @JsonDeserialize(as=HashMultimap.class)
    private Multimap<String,Object> metaData;
    private String etag; // etag for the text
    private transient String text;

    public static Page parse(String s) throws IOException {
        GuideMapper mapper = new GuideMapper();
        return mapper.readValue(s, Page.class);
    }

    public Page() {
        images = new ArrayList<>();
        pageLinks = new ArrayList<>();
        metaData = HashMultimap.create();
        guideName = "";
    }

    public Page(@NonNull String guideName, int id) {
        this();
        this.guideName = guideName;
        this.id = id;
    }

    public Page copy() {
        try {
            return parse(toJSONString());
        } catch (IOException e) {
            // should be OK as we're parsing a known page
            throw new RuntimeException(e);
        }
    }

    @Override
    public int compareTo(Page other) {
        return getId() - other.getId();
    }

    public void addMeta(@NonNull String key, String val) {
        Multimap<String,Object> meta = getMetaData();
        if (meta == null) {
            meta = HashMultimap.create();
        }
        meta.put(key, val);
        setMetaData(meta);
    }

    @JsonIgnore
    public PageRef getPageRef() {
        return new PageRef(guideName, id);
    }

    @JsonIgnore
    public PageRef getPageRef(String name) {
        return new PageRef(guideName.equals(name) ? "" : guideName, id);
    }

    @JsonIgnore
    public Set<String> getTags() {
        Collection<Object> obj = getMetaData().get("tag");
        Set<String> out = new HashSet<>();
        for (Object o : obj) {
            out.add(o.toString());
        }
        return out;
    }

    @JsonIgnore
    public String getImage() {
        return (images == null || images.size() == 0) ? null : images.get(0).getSrc();
    }

    public void setImage(@NonNull String image) {
        setPageImage(new PageImage(image));
    }

    public void setPageImage(@NonNull PageImage pageImage) {
        if (images == null) {
            images = new ArrayList<>();
        }
        if (images.size() == 0) {
            images.add(pageImage);
        } else {
            images.set(0, pageImage);
        }
    }

    public void addImage(@NonNull String image) {
        if (images == null) {
            images = new ArrayList<>();
        }
        images.add(new PageImage(image));
    }

    public void addPageImage(@NonNull PageImage pageImage) {
        if (images == null) {
            images = new ArrayList<>();
        }
        images.add(pageImage);
    }

    public Page addTags(@NonNull Set<String> tags) {
        getMetaData().putAll("tag", tags);
        return this;
    }

    public void addTag(@NonNull String tag) {
        getMetaData().put("tag", tag);
    }

    public boolean hasTag(@NonNull String tag) {
        Set<String> tags = getTags();
        return tags.contains(tag);
    }

    public boolean hasTagPattern(@NonNull String tagPattern) {
        return MetaUtil.hasTagPattern(getMetaData(), tagPattern);
    }

    @JsonProperty
    public String getText() {
        return text;
    }

    @JsonIgnore
    public String getDigest() {
        return Digest.digestHex(toJSONString(), Digest.Algorithm.MD5);
    }

    public void setText(String text) {
        if (text != null) {
            setEtag(computeEtag(text));
        }
        this.text = text;
    }

    @JsonIgnore
    public LatLng getLatLng() {
        Location loc = getLocation();
        return (loc == null) ? null : loc.asLatLng();
    }

    public void setLatLng(LatLng latLng) {
        setLocation(new Location(latLng.getLat(), latLng.getLng()));
    }

    public static String computeEtag(String text) {
        return (text == null) ? null : Digest.digestHex(text, Digest.Algorithm.MD5);
    }

    public String toJSONString() {
        ObjectMapper mapper = new GuideMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (IOException e) {
            return "";
        }
    }

    public String digest() {
        return Digest.digestHex(this.toJSONString(), Digest.Algorithm.MD5);
    }
}
