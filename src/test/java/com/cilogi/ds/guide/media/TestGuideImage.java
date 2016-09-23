// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        TestGuideImage.java  (09/07/15)
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


package com.cilogi.ds.guide.media;

import com.cilogi.ds.guide.meta.MetaData;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class TestGuideImage {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(TestGuideImage.class);


    public TestGuideImage() {
    }

    @Before
    public void setUp() {

    }

    @Test
    public void testGetTags() {
        GuideImage image = new GuideImage("image.jpg", 128, 128);
        MetaData map = image.getMetaData();
        map.put("tag", "one");
        map.put("tag", "two");
        image.setMetaData(map);
        Set<String> tags = image.getTags();
        assertEquals(2, tags.size());
        assertTrue(tags.contains("one"));
        assertTrue(tags.contains("two"));
        assertFalse(tags.contains("three"));
    }

    @Test
    public void setTags() {
        GuideImage image = new GuideImage("image.jpg", 128, 128);
        Set<String> tags = new HashSet<>();
        tags.add("one");
        tags.add("two");
        image.setTags(tags);
        //Set<String> back = image.getTags();
        assertEquals(2, tags.size());
        assertTrue(tags.contains("one"));
        assertTrue(tags.contains("two"));
        assertFalse(tags.contains("three"));
    }
}