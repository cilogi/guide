// Copyright (c) 2016 Cilogi. All Rights Reserved.
//
// File:        MetaData.java  (9/22/16)
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


package com.cilogi.ds.guide.meta;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.reinert.jjschema.SchemaIgnore;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Meta data for guide components.
 */
@EqualsAndHashCode
@ToString
@SuppressWarnings({"unused"})
public class MetaData implements Serializable {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(MetaData.class);

    private static final String TAG_KEY = "tag";
    private static final String INDEX_KEY = "index";
    private static final long serialVersionUID = 5123700827418471368L;

    @SchemaIgnore
    @JsonDeserialize(as = HashMultimap.class)
    private SetMultimap<String,Object> data;

    public MetaData() {
        data = HashMultimap.create();
    }

    public MetaData(@NonNull Multimap<String,Object> data) {
        this.data = HashMultimap.create(data);
    }

    public MetaData(MetaData meta) {
        data = (meta == null) ? HashMultimap.<String,Object>create() : HashMultimap.create(meta.data);
    }

    public int size() {
        return data.size();
    }

    public Set<Object> get(@NonNull String key) {
        return data.get(key);
    }

    public MetaData removeAll(@NonNull String key) {
        data.removeAll(key);
        return this;
    }

    public MetaData put(@NonNull String key, Object val) {
        data.put(key, val);
        return this;
    }

    public MetaData putAll(@NonNull Multimap<String,Object> map) {
        data.putAll(map);
        return this;
    }

    public boolean containsEntry(@NonNull String key, Object value) {
        return data.containsEntry(key, value);
    }

    public Set<String> getString(@NonNull String key) {
        Set<Object> obj = data.get("tag");
        Set<String> out = new HashSet<>();
        for (Object o : obj) {
            out.add(o.toString());
        }
        return out;
    }

    public Object getFirst(@NonNull String key) {
        Set<Object> all = get(key);
        return (all.size() == 0) ? null : all.iterator().next();
    }

    public String getFirstString(@NonNull String key) {
        Object val = getFirst(key);
        return (val == null) ? null : val.toString();
    }

    public MetaData addTags(@NonNull Set<String> tags) {
        data.putAll(TAG_KEY, tags);
        return this;
    }

    public MetaData addTag(@NonNull String tag) {
        data.put(TAG_KEY, tag);
        return this;
    }

    public boolean hasTag(@NonNull String tag) {
        Set<String> tags = getTags();
        return tags.contains(tag);
    }

    public void setTags(@NonNull Set<String> tags) {
        data.removeAll(TAG_KEY);
        addTags(tags);
    }

    @JsonIgnore
    public Set<String> getTags() {
        return getString(TAG_KEY);
    }

    @JsonIgnore
    public Integer getIndex() {
        Set<Object> c = data.get(INDEX_KEY);
        if (c.size() == 0) {
            return null;
        } else {
            String s = c.iterator().next().toString();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                LOG.warn("Can't parse <" + s + "> as Integer index");
                return null;
            }
        }
    }

    public MetaData setIndex(Integer index) {
        data.removeAll(INDEX_KEY);
        if (index != null) {
            data.put(INDEX_KEY, Integer.toString(index));
        }
        return this;
    }

    public boolean hasTagPattern(@NonNull String tagPattern) {
        Set<Object> tags = data.get("tag");

        if (data.containsKey(tagPattern)) {
            return true;
        }
        if (tagPattern.endsWith(":")) {
            for (Object tagObject : tags) {
                if (tagObject.toString().startsWith(tagPattern)) {
                    return true;
                }
            }
        } else if (tagPattern.startsWith(":")) {
            for (Object tagObject : tags) {
                if (tagObject.toString().endsWith(tagPattern)) {
                    return true;
                }
            }
        }
        return false;
    }

    @JsonProperty
    public SetMultimap<String,Object> getData() {
        return data;
    }
}
