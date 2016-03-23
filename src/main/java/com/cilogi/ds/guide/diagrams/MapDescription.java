// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        DefaultMapInfo.java  (04/11/15)
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

import com.cilogi.ds.guide.mapper.LatLng;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Accessors(chain=true)
public class MapDescription implements Serializable {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(MapDescription.class);
    private static final long serialVersionUID = -6693422808725101511L;

    private String title;
    private LatLng tl;
    private LatLng br;
    private LatLng center;

    private MapDescription() {}

    public MapDescription(@NonNull String title) {
        this.title = title;
    }
}
