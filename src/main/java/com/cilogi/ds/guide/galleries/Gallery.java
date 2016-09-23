// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        Gallery.java  (07/09/15)
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


package com.cilogi.ds.guide.galleries;

import com.cilogi.ds.guide.mapper.GuideMapper;
import com.cilogi.ds.guide.meta.MetaData;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Gallery implements Serializable {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(Gallery.class);
    private static final long serialVersionUID = 7119208243401039380L;

    private String id;
    private String title;
    private String description;
    private List<GalleryImage> images;
    private MetaData metaData;

    public static Gallery fromJSON(@NonNull String data) {
        try {
            return new GuideMapper().readValueHjson(data, Gallery.class);
        } catch (Exception e) {
            LOG.warn("Can't parse data " + data);
            return null;
        }
    }

    private Gallery() {
        title = "";
        description="";
        images = new ArrayList<>();
        metaData = new MetaData();
    }

    public Gallery(@NonNull String id) {
        this();
        this.id = id;
    }

    public String toJSONString() {
        try {
            return new GuideMapper().writeValueAsString(this);
        } catch (IOException e) {
            LOG.warn("Can't convert gallery " + this + " to JSON");
            return "{}";
        }
    }

}
