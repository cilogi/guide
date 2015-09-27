// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        TestTour.java  (07/04/15)
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

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TestTour {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(TestTour.class);


    public TestTour() {
    }

    @Before
    public void setUp() {

    }

    @Test
    public void testJSON() throws IOException {
        Tour tour = new Tour("tour", "title", Lists.<TourStop>newArrayList());
        tour.setDescription("text");
        String out = tour.toJSONString();
        Tour back = Tour.fromJSON(out);
        assertEquals(tour, back);
    }

    @Test
    public void testCopy() throws IOException {
        Tour t1 = new Tour("id", "title", Lists.<TourStop>newArrayList(
                new TourStop("guide/1", "intro1"),
                new TourStop("guide/2", "intro2")
        ));
        t1.setDescription("text");
        t1.setBackground("background");
        Tour t2 = new Tour(t1);
        assertEquals(t1, t2);
    }
}