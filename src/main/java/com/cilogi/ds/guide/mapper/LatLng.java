// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        LatLng.java  (26/02/15)
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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;

@EqualsAndHashCode
public class LatLng implements Serializable {
    static final Logger LOG = LoggerFactory.getLogger(LatLng.class);
    private static final long serialVersionUID = 6843840948228234483L;

    @Getter
    private double lat;
    @Getter
    private double lng;

    public static LatLng parseComma(String s) {
        String[] sub = s.trim().split(",");
        if (sub.length == 2) {
            try {
                return new LatLng(Double.parseDouble(sub[0]), Double.parseDouble(sub[1]));
            } catch (NumberFormatException e) {
                LOG.info("Can't parse " + s + " as latlng");
                return null;
            }
        } else {
            LOG.info("Can't parse " + s + " as latlng");
            return null;
        }
    }

    public static Bounds bounds(Collection<LatLng> points) {
        Bounds bounds = new Bounds();
        for (LatLng point : points) {
            bounds.expand(point);
        }
        return bounds;
    }

    private LatLng() {}

    public LatLng(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public LatLng(LatLng ll) {
        this(ll.lat, ll.lng);
    }


    public static class Bounds {
        @Getter
        private LatLng tl;
        @Getter
        private LatLng br;
        Bounds() {
            tl = new LatLng(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
            br = new LatLng(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY);
        }
        public LatLng center() {
            return new LatLng(tl.lat + (br.lat - tl.lat)/2.0, tl.lng + (br.lng - tl.lng)/2.0);
        }

        Bounds expand(@NonNull LatLng point) {
            if (point.lat > tl.lat) tl.lat = point.lat;
            if (point.lng < tl.lng) tl.lng = point.lng;

            if (point.lat < br.lat) br.lat = point.lat;
            if (point.lng > br.lng) br.lng = point.lng;
            return this;
        }
    }
}
