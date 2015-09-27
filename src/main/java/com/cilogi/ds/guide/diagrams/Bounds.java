// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        Bounds.java  (23/01/15)
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

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.vecmath.Point2d;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Bounds implements Serializable {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(Bounds.class);
    private static final long serialVersionUID = -7857336168290615741L;

    private Point2d tl;
    private Point2d br;
    private Point2d center;

    public Bounds() {
        tl = new Point2d();
        br = new Point2d();
        center = null;
    }

    public double[] center() {
        if (center == null) {
            Point2d center = new Point2d();
            center.add(tl);
            center.add(br);
            center.scale(0.5);
            return new double[]{center.x, center.y};
        } else {
            return new double[]{center.x, center.y};
        }
    }

}
