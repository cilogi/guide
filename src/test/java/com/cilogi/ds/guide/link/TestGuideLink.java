// Copyright (c) 2016 Cilogi. All Rights Reserved.
//
// File:        TestGuideLink.java  (10/17/16)
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


package com.cilogi.ds.guide.link;

import com.cilogi.util.IOUtil;
import org.junit.Before;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class TestGuideLink {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(TestGuideLink.class);


    public TestGuideLink() {
    }

    @Before
    public void setUp() {

    }

    @Test
    public void testText() throws IOException {
        String text = IOUtil.loadStringUTF8(getClass().getResource("links.txt"));
        List<GuideLink> links = GuideLink.guideLinks(text);
        assertEquals(4, links.size());
    }
}