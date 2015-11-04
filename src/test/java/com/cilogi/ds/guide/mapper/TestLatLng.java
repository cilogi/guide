// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        TestLatLng.java  (18/09/15)
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

import org.junit.Before;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class TestLatLng {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(TestLatLng.class);


    public TestLatLng() {
    }

    @Before
    public void setUp() {

    }

    @Test
    public void testParse() {
        LatLng val = LatLng.parseComma("55.882611,-4.289905");
        assertNotNull(val);
        assertEquals(val.getLat(), 55.882611, 1e-8);
        assertEquals(val.getLng(), -4.289905, 1e-8);
    }

    @Test
    public void testBounds() {
        List<LatLng> list = Arrays.asList(
                new LatLng[]{new LatLng(55, -4), new LatLng(57, -6)}
        );
        LatLng.Bounds bounds = LatLng.bounds(list);
        LatLng tl = bounds.getTl();
        LatLng br = bounds.getBr();
        assertEquals(57, tl.getLat(), 1e-8);
        assertEquals(-6, tl.getLng(), 1e-8);
        assertEquals(55, br.getLat(), 1e-8);
        assertEquals(-4, br.getLng(), 1e-8);
    }
}