// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        GuideAudio.java  (27/04/15)
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


package com.cilogi.ds.guide.media;

import com.cilogi.ds.guide.ITextFilter;
import com.cilogi.ds.guide.ITextFilterable;
import com.cilogi.ds.guide.meta.MetaData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain=true)
@SuppressWarnings({"unused"})
public class GuideAudio implements Serializable, ITextFilterable {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(GuideAudio.class);
    private static final long serialVersionUID = -3642718837956551455L;

    private String id;
    private String url;
    private String title;
    private String description;
    private int duration;
    private String digest;
    private MetaData metaData;

    private GuideAudio() {
        metaData = new MetaData();
    }

    public GuideAudio(@NonNull String id) {
        this();
        this.id = id;
    }

    public GuideAudio(@NonNull GuideAudio audio) {
        id = audio.id;
        url = audio.url;
        title = audio.title;
        description = audio.description;
        duration = audio.duration;
        digest = audio.digest;
        metaData = new MetaData(audio.metaData);
    }

    public Set<String> getTags() {
        Collection<Object> tags = getMetaData().get("tag");
        Set<String> out = Sets.newHashSet();
        for (Object tag: tags) {
            out.add(tag.toString());
        }
        return out;
    }


    public GuideAudio setTags(@NonNull Set<String> tags) {
        getMetaData().setTags(tags);
        return this;
    }

    @Override
    public void filter(ITextFilter filter) {
        title = filter.filter(title);
        description = filter.filter(description);
    }
}
