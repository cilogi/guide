// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        TestLink.java  (01/08/15)
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


package com.cilogi.ds.guide.sourcerepository;

import org.junit.Before;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class TestSourceRepository {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(TestSourceRepository.class);


    public TestSourceRepository() {
    }

    @Before
    public void setUp() {

    }

    @Test
    public void testJSON() {
        SourceRepository link = new SourceRepository(SourceRepositoryType.Dropbox, "guideName")
                .token("token")
                .userID(1)
                .url("url");
        String s = link.toJSONString();
        SourceRepository back = SourceRepository.fromJSONString(s);
        assertEquals(link, back);
    }
}