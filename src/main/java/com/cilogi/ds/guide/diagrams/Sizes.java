// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        Sizes.java  (23/01/15)
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
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
@ToString
@Data
public class Sizes implements Serializable {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(Sizes.class);
    private static final long serialVersionUID = 5996143240638277308L;

    private double min;
    private double max;
    private double threshold;

    public Sizes() {

    }

    public Map<String, Double> sizeMap(Zoom zoom) {
        Map<String, Double> out = new HashMap<>();
        double range = zoom.getMax() - zoom.getMin();

        for (int level = zoom.getMin(); level <= zoom.getMax(); level++) {
            double percent = (level - zoom.getMin()) / range;
            double val = min + percent * (max - min);
            out.put(Integer.toString(level), val);
        }
        out.put("threshold", threshold);
        return out;
    }
}
