// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        Listing.java  (07/04/15)
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


package com.cilogi.ds.guide.listings;

import com.cilogi.ds.guide.IGuide;
import com.cilogi.ds.guide.mapper.GuideMapper;
import com.cilogi.ds.guide.pages.Page;
import com.cilogi.util.MetaUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;
import org.hjson.JsonValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Get a set of pages which contain all of a given set of tags, so a set intersection.
 * <p>Matching is a little quirky.  If a query tag if of the form <code>{{name}}:</code>
 * with a colon at the end then the hit tag just has to match this prefix.  So that query
 * <code>car:</code> will match <code>car:mercedes</code></p>.  Similarly a colon at the
 * front means that <code>:mercedes</code> will match <code>truck:mercedes</code> as well
 * as <code>car:mercedes</code>.  If there is no trailing or leading colon then the match
 * is exact, with case counting as different.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Accessors(chain = true)
@JsonIgnoreProperties({"pageIds"})
public class Listing implements Serializable {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(Listing.class);
    private static final long serialVersionUID = -2489263313762667827L;

    @SuppressWarnings(("unused"))
    public static enum Type {
        text,
        hybrid,
        image
    }

    private String id;
    private Type type;
    private String title;
    private String description;
    private String background;
    private Set<String> tags;
    private SetMultimap<String,Object> metaData;
    private transient Set<Integer> pageIds;

    @SuppressWarnings({"unused"})
    public static Listing fromJSON(String data) throws IOException {
        GuideMapper mapper = new GuideMapper();
        return mapper.readValue(JsonValue.readHjson(data).toString(), Listing.class);
    }


    @SuppressWarnings({"unused"})
    private Listing() {
        type = Type.text;
        tags = new LinkedHashSet<>();
        pageIds = new HashSet<>();
        metaData = HashMultimap.create();
    }

    public Listing(@NonNull String id, @NonNull Set<String> tags) {
        this.id = id;
        type = Type.text;
        this.tags = new LinkedHashSet<>(tags);
        this.pageIds = new HashSet<>();
        metaData = HashMultimap.create();
    }

    public Listing init(@NonNull IGuide guide) {
        for (Page page : guide.getPages()) {
            Set<String> pageTags = page.getTags();
            boolean allHit = true;
            for (String tag : tags) {
                if (!hit(tag, pageTags)) {
                    allHit = false;
                    break;
                }
            }
            if (allHit) {
                pageIds.add(page.getId());
            }
        }
        return this;
    }

    public int size() {
        return pageIds.size();
    }

    @JsonIgnore
    public Integer getIndex() {
        return MetaUtil.getIndex(getMetaData());
    }

    public void setIndex(Integer index) {
        MetaUtil.setIndex(index, getMetaData());
    }

    @JsonDeserialize(as=LinkedHashSet.class)
    @SuppressWarnings({"unused"})
    public Listing setTags(Set<String> tags) {
        this.tags = tags;
        return this;
    }

    @SuppressWarnings({"unused"})
    public static boolean hit(@NonNull Set<String> searchTags, @NonNull Set<String> pageTags) {
        boolean allHit = true;
        for (String tag : searchTags) {
            if (!hit(tag, pageTags)) {
                allHit = false;
                break;
            }
        }
        return allHit;
    }

    private static boolean hit(@NonNull final String tag, @NonNull final Set<String> pageTags) {
        boolean hit = false;
        if (tag.endsWith(":")) {
            for (String pageTag : pageTags) {
                if (pageTag.startsWith(tag)) {
                    hit = true;
                }
            }
        } else if (tag.startsWith(":")) {
            for (String pageTag : pageTags) {
                if (pageTag.endsWith(tag)) {
                    hit = true;
                }
            }
        } else {
            for (String pageTag : pageTags) {
                if (pageTag.equals(tag)) {
                    hit = true;
                }
            }
        }
        return hit;
    }

    public String toJSONString() {
        try {
            return new GuideMapper().writeValueAsString(this);
        } catch (IOException e) {
            LOG.warn("Can't convert listing " + this + " to JSON");
            return "{}";
        }
    }
}
