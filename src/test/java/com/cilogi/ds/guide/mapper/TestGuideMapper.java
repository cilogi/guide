// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        TestGuideMapper.java  (18/08/15)
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

import java.io.IOException;

import static org.junit.Assert.*;

public class TestGuideMapper {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(TestGuideMapper.class);


    public TestGuideMapper() {
    }

    @Before
    public void setUp() {

    }

    @Test
    public void testLatLngRead() {
        String s = "[100,200]";
        GuideMapper mapper = new GuideMapper();
        try {
            LatLng ll = mapper.readValue(s, LatLng.class);
            assertEquals(new LatLng(100,200), ll);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testLatLngWrite() throws IOException {
        LatLng ll = new LatLng(100,200);
        String s = new GuideMapper().writeValueAsString(ll);
        LatLng back = new GuideMapper().readValue(s, LatLng.class);
        assertEquals(ll, back);

    }

}