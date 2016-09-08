// Copyright (c) 2016 Cilogi. All Rights Reserved.
//
// File:        TestWikipageInfo.java  (9/8/16)
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


package com.cilogi.ds.guide.wiki;

import com.cilogi.util.IOUtil;
import org.junit.Before;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class TestWikiPageInfo {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(TestWikiPageInfo.class);


    public TestWikiPageInfo() {
    }

    @Before
    public void setUp() {

    }

    @Test
    public void testLoad() throws IOException {
        String s = IOUtil.loadStringUTF8(getClass().getResource("wikiPages.json"));
        List<WikiPageInfo> infos = WikiPageInfo.getJSON(s);
        assertEquals(2, infos.size());
    }

    @Test
    public void testDefault() {
        WikiPageInfo info = new WikiPageInfo();
        String s = info.toJSONString();
        assertEquals("{}", s);
    }
}