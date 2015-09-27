// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        TestMaps.java  (23/01/15)
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


package com.cilogi.ds.guide.diagrams;

import com.cilogi.util.IOUtil;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TestMaps {
    static final Logger LOG = LoggerFactory.getLogger(TestMaps.class);


    public TestMaps() {
    }

    @Before
    public void setUp() {

    }

    @Test
    public void testLoad() throws IOException {
        String spec = IOUtil.loadStringUTF8(getClass().getResource("test-maps.json"));
        Diagrams maps = Diagrams.parse(spec);
        String out = maps.toJSONString();
        Diagrams back = Diagrams.parse(out);
        assertEquals(maps, back);
    }
}