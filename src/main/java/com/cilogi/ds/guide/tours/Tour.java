// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        Tour.java  (12/03/15)
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


package com.cilogi.ds.guide.tours;

import com.cilogi.ds.guide.mapper.GuideMapper;
import com.cilogi.ds.guide.mapper.Location;
import com.cilogi.util.MetaUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.SetMultimap;
import lombok.Data;
import lombok.NonNull;
import org.hjson.JsonValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tour implements Serializable {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(Tour.class);
    private static final long serialVersionUID = -2253902774746769708L;

    private String id;
    private String title;
    private String background;
    private String description;
    private Location location;
    private List<TourStop> stops;
    private SetMultimap<String,Object> metaData;

    public static Tour fromJSON(String data) throws IOException {
        GuideMapper mapper = new GuideMapper();
        return mapper.readValue(JsonValue.readHjson(data).toString(), Tour.class);
    }

    public static List<Tour> copy(List<Tour> list) {
        List<Tour> out = Lists.newArrayListWithCapacity(list.size());
        for (Tour tour: list) {
            out.add(new Tour(tour));
        }
        return out;
    }

    public Tour() {
        stops = new ArrayList<>();
        metaData = HashMultimap.create();
    }

    public Tour(Tour t) {
        this.id = t.id;
        this.title = t.title;
        this.background = t.background;
        this.description = t.description;
        this.location = t.location;
        this.stops = new ArrayList<>();
        for (TourStop stop : t.stops) {
            this.stops.add(new TourStop(stop));
        }
        this.metaData = HashMultimap.create(t.getMetaData());
    }

    public Tour(String id, String title, List<TourStop> stops) {
        this.id = id;
        this.title = title;
        this.stops = stops;
        this.metaData = HashMultimap.create();
    }

    public Tour makePublic(@NonNull String guideName) {
        for (TourStop stop: stops) {
            stop.makePublic(guideName);
        }
        return this;
    }

    @JsonIgnore
    public Integer getIndex() {
        return MetaUtil.getIndex(getMetaData());
    }

    public void setIndex(Integer index) {
        MetaUtil.setIndex(index, getMetaData());
    }

    public String toJSONString() {
        try {
            return new GuideMapper().writeValueAsString(this);
        } catch (IOException e) {
            LOG.warn("Can't convert tour " + this + " to JSON");
            return "{}";
        }
    }

}
