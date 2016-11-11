// Copyright (c) 2016 Cilogi. All Rights Reserved.
//
// File:        TourStopSerializer.java  (11-Nov-16)
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
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class TourStopSerializer extends JsonSerializer<TourStop> {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(TourStopSerializer.class);

    private final JsonSerializer<Object> defaultSerializer;

    TourStopSerializer(JsonSerializer<Object> defaultSerializer) {
        this.defaultSerializer = defaultSerializer;
    }


    @Override
    public void serialize(TourStop stop, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (isNaked(stop)) {
            gen.writeString(stop.getId());
        } else {
            defaultSerializer.serialize(stop, gen, provider);
        }
    }

    private static final boolean isNaked(TourStop stop) {
        return isEmpty(stop.getIntro()) && isEmpty(stop.getExtro()) && isEmpty(stop.getTitle())
                && stop.getLocation() == null && stop.getMetaData().size() == 0;
    }

    private static boolean isEmpty(String s) {
        return s == null || "".equals(s);
    }
}
