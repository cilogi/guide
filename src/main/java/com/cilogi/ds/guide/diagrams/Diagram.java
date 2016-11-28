// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        Map.java  (23/01/15)
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


package com.cilogi.ds.guide.diagrams;

import com.cilogi.ds.guide.filter.ITextFilter;
import com.cilogi.ds.guide.filter.ITextFilterable;
import com.cilogi.ds.guide.mapper.GuideMapper;
import com.cilogi.ds.guide.meta.MetaData;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@JsonPropertyOrder(alphabetic=true)
public class Diagram implements Serializable, ITextFilterable {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(Diagram.class);
    private static final long serialVersionUID = 1079861823817675837L;

    private String name;
    private String guideName;
    private String title;
    private String provider;
    private ImageSpec image;
    private List<Marker> markers;
    private List<Item> items;
    private Bounds bounds;
    private Zoom zoom;
    private Sizes sizes;
    private boolean showLocation;
    private boolean cluster;
    private boolean listed;
    private MetaData metaData;


    public static Diagram fromJSON(String data) throws IOException {
        GuideMapper mapper = new GuideMapper();
        return mapper.readValueHjson(data, Diagram.class);
    }

    public Diagram() {
        markers = Lists.newArrayList();
        items = Lists.newArrayList();
        metaData = new MetaData();
    }

    public Diagram copy() {
        try {
            return fromJSON(this.toJSONString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setMarkers(@NonNull List<Marker> markers) {
        this.markers = Lists.newArrayListWithCapacity(markers.size());
        for (Marker marker : markers) {
            this.markers.add(new Marker(marker));
        }
    }

    @Override
    public void filter(ITextFilter filter) {
        title = filter.filter(title);
        for (ITextFilterable item : items) {
            item.filter(filter);
        }
    }

    @JsonIgnore
    public Integer getIndex() {
        return getMetaData().getIndex();
    }

    public void setIndex(Integer index) {
        getMetaData().setIndex(index);
    }

    public String toJSONString() {
        try {
            GuideMapper mapper = new GuideMapper();
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
