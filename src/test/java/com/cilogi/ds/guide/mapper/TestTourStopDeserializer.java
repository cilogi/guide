// Copyright (c) 2016 Cilogi. All Rights Reserved.
//
// File:        TestTourStopDeserializer.java  (11-Nov-16)
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

import com.cilogi.ds.guide.tours.Tour;
import com.cilogi.ds.guide.tours.TourStop;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import org.junit.Before;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class TestTourStopDeserializer {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(TestTourStopDeserializer.class);


    public TestTourStopDeserializer() {
    }

    @Before
    public void setUp() {

    }

    @Test
    public void testTourStopInt() throws IOException {
        String val = "25";
        MyMapper mapper = new MyMapper();
        TourStop stop = mapper.readValue(val, TourStop.class);
        assertEquals("25", stop.getId());
    }

    @Test
    public void testTourStopString() throws IOException {
        String val = "\"guide/25\"";
        MyMapper mapper = new MyMapper();
        TourStop stop = mapper.readValue(val, TourStop.class);
        assertEquals("\"guide/25\"", stop.getId());
    }

    @Test
    public void testTourStopObject() throws IOException {
        String val = "{\"id\":\"guide/25\"}";
        MyMapper mapper = new MyMapper();
        TourStop stop = mapper.readValue(val, TourStop.class);
        assertEquals("guide/25", stop.getId());
    }

    @Test
    public void testTourStopArray() throws IOException {
        String val = "{\"id\":\"tour\", \"stops\":[1,2,3,{\"id\":4}]}";
        MyMapper mapper = new MyMapper();
        Tour tour = mapper.readValue(val, Tour.class);
        List<TourStop> stops = tour.getStops();
        assertEquals(4, stops.size());
    }


    static class MyMapper extends ObjectMapper {
        MyMapper() {
            registerModule(new GuavaModule());
            SimpleModule module = new SimpleModule();
            module.addDeserializer(TourStop.class, new TourStopDeserializer());
            registerModule(module);
            setVisibility(getVisibilityChecker().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
    }
}