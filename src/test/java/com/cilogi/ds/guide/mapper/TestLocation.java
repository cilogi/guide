// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        TestLocation.java  (20/10/15)
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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.Assert.*;

public class TestLocation {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(TestLocation.class);


    public TestLocation() {}

    @Before
    public void setUp() {}

    @Test
    public void testParseAsLatLng() {
        Location val = Location.parse("55.882611,-4.289905");
        assertNotNull(val);
        assertEquals(val.getX(), 55.882611, 1e-8);
        assertEquals(val.getY(), -4.289905, 1e-8);
    }

    @Test
    public void testParseAsLocation() {
        Location val = Location.parse("fred.jpg , 45, 55");
        assertNotNull(val);
        assertEquals("fred.jpg", val.getImage());
        assertEquals(val.getX(), 45, 1e-8);
        assertEquals(val.getY(), 55, 1e-8);
    }

    @Test
    public void testParseAsJSONLatLng() {
        Location val = Location.parse("[55.882611,-4.289905]");
        assertNotNull(val);
        assertEquals(val.getX(), 55.882611, 1e-8);
        assertEquals(val.getY(), -4.289905, 1e-8);
    }

    @Test
    public void testParseAsJSONLocation() {
        Location val = Location.parse("\"fred.jpg\" , 45, 55]");
        assertNotNull(val);
        assertEquals("fred.jpg", val.getImage());
        assertEquals(val.getX(), 45, 1e-8);
        assertEquals(val.getY(), 55, 1e-8);
    }

    @Test
    public void testAsLatLng() {
        Location loc = new Location(1, 2);
        assertEquals(new LatLng(1,2), loc.asLatLng());
    }

    @Test
    public void testIsLatLng() {
        Location loc = new Location(1, 2);
        assertTrue(loc.isLatLng());

        loc = new Location("image", 1, 2);
        assertFalse(loc.isLatLng());
    }

    @Test
    public void testJsonWithImage() throws IOException {
        Location loc = new Location("image", 1, 2);
        String s = new ObjectMapper().writeValueAsString(loc);
        Location back = new ObjectMapper().readValue(s, Location.class);
        assertEquals(loc, back);
    }

    @Test
    public void testJsonWithoutImage() throws IOException {
        Location loc = new Location(1, 2);
        String s = new ObjectMapper().writeValueAsString(loc);
        Location back = new ObjectMapper().readValue(s, Location.class);
        assertEquals(loc, back);
    }

    @Test
    public void testSerializeWithImage() throws IOException {
        Location loc = new Location("image", 1, 2);
        String s = new GuideMapper().writeValueAsString(loc);
        assertEquals("[\"image\",1.0,2.0]", s);
    }

    @Test
    public void testSerializeWithoutImage() throws IOException {
        Location loc = new Location(1, 2);
        String s = new GuideMapper().writeValueAsString(loc);
        assertEquals("[1.0,2.0]", s);
    }

    @Test
    public void testDeserializeWithImage() throws IOException {
        Location loc = new GuideMapper().readValue("[\"image\",1.0,2.0]", Location.class);
        assertEquals(new Location("image", 1, 2), loc);
    }

    @Test
    public void testDeserializeWithoutImage() throws IOException {
        Location loc = new GuideMapper().readValue("[1.0,2.0]", Location.class);
        assertEquals(new Location(1, 2), loc);
    }

    @Test
    public void testDeserializeNumericImage() throws IOException {
        assertEquals(new Location("1", 2,3), new GuideMapper().readValue("[1,2,3]", Location.class));
    }

    @Test
    public void testDeserializeTooManyArgs() throws IOException {
        assertNull(new GuideMapper().readValue("[1,2,3,4]", Location.class));
    }

    @Test
    public void testDeserializeBadDoubles() throws IOException {
        try {
            new GuideMapper().readValue("[\"a\",\"b\"]", Location.class);
            fail("should throw ClassCastException");
        } catch (ClassCastException e) {
            // ok
        }
    }

}