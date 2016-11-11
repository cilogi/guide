// Copyright (c) 2016 Cilogi. All Rights Reserved.
//
// File:        TestTourStopSerializer.java  (11-Nov-16)
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
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import org.junit.Before;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.Assert.*;

public class TestTourStopSerializer {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(TestTourStopSerializer.class);


    public TestTourStopSerializer() {
    }

    @Before
    public void setUp() {

    }

    @Test
    public void testSerializeNaked() throws IOException {
        TourStop stop = new TourStop("25", null);
        String out = new MyMapper().writeValueAsString(stop);
        assertEquals("\"25\"", out);
    }

    @Test
    public void testSerializeNonNaked() throws IOException {
        TourStop stop = new TourStop("25", "This is a stop");
        String out = new MyMapper().writeValueAsString(stop);
        assertEquals("{\"id\":\"25\",\"intro\":\"This is a stop\"}", out);
    }

    static class MyMapper extends ObjectMapper {
        MyMapper() {
            registerModule(new GuavaModule());
            SimpleModule module = new SimpleModule();
            module.setSerializerModifier(new MyClassSerializerModifier());
            registerModule(module);
            setVisibility(getVisibilityChecker().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
    }

    static class MyClassSerializerModifier extends BeanSerializerModifier {
        @Override
        public JsonSerializer<?> modifySerializer(SerializationConfig config, BeanDescription beanDesc, JsonSerializer<?> serializer) {
            if (beanDesc.getBeanClass() == TourStop.class) {
                return new TourStopSerializer((JsonSerializer<Object>)serializer);
            }
            return serializer;
        }
    }
}