// Copyright (c) 2016 Cilogi. All Rights Reserved.
//
// File:        TourStopDeserializer.java  (11-Nov-16)
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

import com.cilogi.ds.guide.tours.TourStop;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.guava.GuavaModule;

import java.io.IOException;

/** Deserialize tour stops, with the twist that a naked integer or string is OK as the stop.
 *  This is simply here to make it simple to create tours, with the simplest tour being just
 *  an id and a list of ints.
 */
class TourStopDeserializer extends JsonDeserializer<TourStop> {

    private static TourMapper tourMapper = new TourMapper();

    TourStopDeserializer() {}

    @Override
    public TourStop deserialize(JsonParser jp, DeserializationContext _any)
            throws IOException {
        TreeNode node = jp.getCodec().readTree(
                jp);
        if (node.isValueNode()) {
            return new TourStop(node.toString(), null);
        } else {
            return tourMapper.convertValue(node, TourStop.class);
        }
    }

    private static class TourMapper extends ObjectMapper {
        private static final long serialVersionUID = -8226475103885871803L;

        TourMapper() {
            registerModule(new GuavaModule());
            SimpleModule module = new SimpleModule();
            module.addSerializer(Location.class, new LocationSerializer());
            module.addDeserializer(Location.class, new LocationDeserializer());
            registerModule(module);
        }
    }
}
