// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        Marker.java  (23/01/15)
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@Data
public class Marker implements Serializable {
    static final Logger LOG = LoggerFactory.getLogger(Marker.class);
    private static final long serialVersionUID = 8622532703963262102L;


    private String id;
    private String icon;
    private String iconColor;
    private String color;
    private String theme;
    private String shape;

    private Marker() {
        this.icon = "fa-star";
        this.color = "green";
        this.iconColor = "white";
        this.theme = "Item";
        this.shape = "square";
    }

    public Marker(String id) {
        this();
        this.id = id;
    }

    public Marker(Marker m) {
        this.id = m.id;
        this.icon = m.icon;
        this.iconColor = m.iconColor;
        this.color = m.color;
        this.theme = m.theme;
        this.shape = m.shape;
    }
}

