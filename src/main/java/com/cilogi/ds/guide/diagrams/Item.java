// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        Item.java  (23/01/15)
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
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.vecmath.Point2d;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
@Data
public class Item implements Serializable {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(Item.class);
    private static final long serialVersionUID = 2019024085803422279L;

    @JsonProperty
    protected String id;
    @JsonProperty
    protected String description;
    @JsonProperty
    protected Point2d pixel;
    @JsonProperty
    protected Point2d latlng;
    @JsonProperty
    protected String marker;
    @JsonProperty
    protected String text;
    @JsonProperty
    protected String icon;

    protected Item() {
        id = "-1";
        description = null;
        pixel = null;
        latlng = null;
        marker = null;
        text = null;
    }
}
