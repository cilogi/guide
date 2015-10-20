// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        Location.java  (20/10/15)
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


package com.cilogi.ds.guide.mapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Location {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(Location.class);

    private final String image;
    private final double x;
    private final double y;


    public static Location parse(String s) {
        String[] sub = s.trim().split(",");
        if (sub.length == 3) {
            return new Location(sub[0].trim(), Double.parseDouble(sub[1]), Double.parseDouble(sub[2]));
        } else if (sub.length == 2) {
            try {
                return new Location(Double.parseDouble(sub[0]), Double.parseDouble(sub[1]));
            } catch (NumberFormatException e) {
                LOG.info("Can't parse " + s + " as latlng");
                return null;
            }
        } else {
            LOG.info("Can't parse " + s + " as location");
            return null;
        }
    }

    @JsonCreator
    public Location(@JsonProperty("image") String image, @JsonProperty("x") double x, @JsonProperty("y") double y) {
        this.image = image;
        this.x = x;
        this.y = y;
    }

    public Location(double x, double y) {
        this.image = null;
        this.x = x;
        this.y = y;
    }

    public Location(@NonNull LatLng latlng) {
        this(latlng.getLat(), latlng.getLng());
    }

    @JsonIgnore
    public boolean isLatLng() {
        return image == null;
    }

    public LatLng asLatLng() {
        return new LatLng(x, y);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Location(");
        if (getImage() != null) {
            builder.append(getImage()).append(",");
        }
        builder.append(x)
            .append(",")
            .append(y)
            .append(")");
        return builder.toString();
    }
}
