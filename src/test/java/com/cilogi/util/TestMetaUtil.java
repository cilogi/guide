// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        TestMetaUtil.java  (14/05/15)
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


package com.cilogi.util;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;
import org.junit.Before;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class TestMetaUtil {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(TestMetaUtil.class);


    public TestMetaUtil() {
    }

    @Before
    public void setUp() {

    }

    @Test
    public void testTagPattern() {
        Multimap<String,Object> map = HashMultimap.create();
        map.put("tag", "image-display:one");
        map.put("tag", "fred");
        assertTrue(MetaUtil.hasTagPattern(map, "image-display:"));
        assertTrue(MetaUtil.hasTagPattern(map, ":one"));
    }

    @Test
    public void testIndexString() {
        SetMultimap<String,Object> map = HashMultimap.create();
        map.put("index", "1");
        assertEquals(new Integer(1), MetaUtil.getIndex(map));
    }

    @Test
    public void testIndexInt() {
        SetMultimap<String,Object> map = HashMultimap.create();
        map.put("index", 1);
        assertEquals(new Integer(1), MetaUtil.getIndex(map));
    }

}