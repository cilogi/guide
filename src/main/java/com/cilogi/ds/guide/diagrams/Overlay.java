// Copyright (c) 2016 Cilogi. All Rights Reserved.
//
// File:        Overlay.java  (28-Nov-16)
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

import com.cilogi.ds.guide.mapper.GuideMapper;
import com.cilogi.util.Digest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonPropertyOrder(alphabetic=true)
@Data
/**
 * An Overlay contains all the information needed to create map tiles containing overlay
 * information.  The idea is that the overlay can be sent to an external service, such as AWS Lambda,
 * for the required tiles to be created.
 */
public class Overlay implements Serializable {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(Overlay.class);
    private static final long serialVersionUID = 4722505605640837010L;
    private static final int DEFAULT_TILESIZE = 256;

    private Bounds bounds;
    private List<ImageSpec> images;
    private String provider;
    private Zoom zoom;
    private int tileSize;

    public static Overlay fromJSON(String data) throws IOException {
         GuideMapper mapper = new GuideMapper();
         return mapper.readValueHjson(data, Overlay.class);
     }


    public Overlay() {
        images = new ArrayList<>();
        bounds = new Bounds();
        zoom = new Zoom();
        tileSize = DEFAULT_TILESIZE;
    }

    public String digest() {
        return Digest.digestHex(toJSONString(), Digest.Algorithm.MD5);
    }

    public String toJSONString() {
        try {
            GuideMapper mapper = new GuideMapper();
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }
}
