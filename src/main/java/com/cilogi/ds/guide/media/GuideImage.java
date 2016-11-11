// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        GuideImage.java  (26/03/15)
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

import com.cilogi.ds.guide.filter.ITextFilter;
import com.cilogi.ds.guide.filter.ITextFilterable;
import com.cilogi.ds.guide.meta.MetaData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.Preconditions;
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
public class GuideImage implements Serializable, ITextFilterable {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(GuideImage.class);
    private static final long serialVersionUID = -8735763701383324241L;

    private String id;
    private String url;
    private String guideName;
    private String title;
    private String description;
    private int width;
    private int height;
    private String digest;
    private MetaData metaData;

    private GuideImage() {
        metaData = new MetaData();
    }

    public GuideImage(@NonNull String id, int width, int height) {
        this();

        Preconditions.checkArgument(width > 0, "Image width for " + id + " must be positive, not " + width);
        Preconditions.checkArgument(height > 0, "Image height for " + id + " must be positive, not " + height);

        this.id = id;
        this.width = width;
        this.height = height;
    }

    public GuideImage(@NonNull GuideImage image) {
        id = image.id;
        url = image.url;
        guideName = image.guideName;
        title = image.title;
        description = image.description;
        width = image.width;
        height = image.height;
        digest = image.digest;
        metaData = new MetaData(image.metaData);
    }

    public Set<String> getTags() {
        Collection<Object> tags = getMetaData().get("tag");
        Set<String> out = Sets.newHashSet();
        for (Object tag: tags) {
            out.add(tag.toString());
        }
        return out;
    }

    public GuideImage setTags(@NonNull Set<String> tags) {
        getMetaData().setTags(tags);
        return this;
    }

    @Override
    public void filter(ITextFilter filter) {
        title = filter.filter(title);
        description = filter.filter(description);
    }
}
