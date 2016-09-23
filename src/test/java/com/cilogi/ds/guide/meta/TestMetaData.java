// Copyright (c) 2016 Cilogi. All Rights Reserved.
//
// File:        TestMetaData.java  (9/22/16)
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


package com.cilogi.ds.guide.meta;

import com.cilogi.ds.guide.mapper.GuideMapper;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import org.junit.Before;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.Assert.*;

public class TestMetaData {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(TestMetaData.class);


    public TestMetaData() {
    }

    @Before
    public void setUp() {

    }

    @Test
    @SuppressWarnings({"unchecked"})
    public void testJson() throws IOException {
        SetMultimap map = HashMultimap.create();
        map.put("a", 1);
        MetaData data = new MetaData();
        data.put("a", 1);
        GuideMapper mapper = new GuideMapper();
        String mapJSON = mapper.writeValueAsString(map);
        String dataJSON = mapper.writeValueAsString(data);
        assertEquals(mapJSON, dataJSON);
    }
}