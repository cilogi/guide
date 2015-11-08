// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        LocationDeserializer.java  (20/10/15)
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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NumericNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.vecmath.Point2d;
import java.io.IOException;


class LocationDeserializer  extends JsonDeserializer<Location> {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(LocationDeserializer.class);

    LocationDeserializer() {

    }

    @Override
    public Location deserialize(JsonParser jp, DeserializationContext _any)
            throws IOException {
        TreeNode node = jp.getCodec().readTree(jp);
        if (node.size() == 3) {
            String image = ((JsonNode) node.get(0)).asText();
            double x = ((NumericNode) node.get(1)).doubleValue();
            double y = ((NumericNode) node.get(2)).doubleValue();
            return new Location(image, x, y);
        } else if (node.size() == 2) {
            double x = ((NumericNode) node.get(0)).doubleValue();
            double y = ((NumericNode) node.get(1)).doubleValue();
            return new Location(x, y);
        } else {
            return null;
        }
    }
}
