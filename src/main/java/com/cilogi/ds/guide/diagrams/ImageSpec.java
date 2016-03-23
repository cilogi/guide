// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        ImageSpec.java  (23/01/15)
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
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
@ToString
@Data
public class ImageSpec implements Serializable {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(ImageSpec.class);
    private static final long serialVersionUID = -6288395652378246150L;

    private String src;
    private double opacity;
    private List<Locate> locates;

    public ImageSpec() {
        src = "";
        opacity = 1.0;
        locates = new ArrayList<>();
    }

    public void setOpacity(double opacity) {
        this.opacity = (opacity > 1.0) ? 1.0 : ((opacity < 0.0) ? 0.0 : opacity);
    }

    public boolean hasLocates() {
        return locates != null && locates.size() > 0;
    }

    public boolean isEmpty() {
        return src == null || "".equals(src);
    }
}
