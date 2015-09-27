// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        TestTourStop.java  (09/04/15)
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


package com.cilogi.ds.guide.tours;

import com.cilogi.ds.guide.mapper.GuideMapper;
import org.junit.Before;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.Assert.*;

public class TestTourStop {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(TestTourStop.class);


    public TestTourStop() {
    }

    @Before
    public void setUp() {

    }

    @Test
    public void testJson() throws IOException {
        TourStop stop = new TourStop("guide/1", "intro");
        stop.setTitle("title");
        stop.setPath("path");
        String s = new GuideMapper().writeValueAsString(stop);
        TourStop back = new GuideMapper().readValue(s, TourStop.class);
        assertEquals("path", back.getPath());
        assertEquals(stop, back); // equals ignores transient fields
    }

    @Test
    public void testConstruct() {
        TourStop stop = new TourStop("guide/23", "intro");
        PageRef ref = stop.getPageRef();
        assertEquals("guide", ref.getGuideName());
        assertEquals(23, ref.getPageIndex());
    }
}