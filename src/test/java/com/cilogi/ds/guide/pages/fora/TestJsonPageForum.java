// Copyright (c) 2016 Cilogi. All Rights Reserved.
//
// File:        TestJsonPageForum.java  (26/02/16)
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


package com.cilogi.ds.guide.pages.fora;

import org.junit.Before;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.*;

public class TestJsonPageForum {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(TestJsonPageForum.class);


    public TestJsonPageForum() {
    }

    @Before
    public void setUp() {

    }

    @Test
    public void testPinnedTopic() {
        JsonPageForum forum = new JsonPageForum("forum");
        forum.addTopic(new Topic("one").pinned(true));
        forum.addTopic(new Topic("two"));
        forum.addTopic(new Topic("three"));
        List<Topic> topics = forum.getTopics();
        assertEquals("one", topics.get(0).getTitle());
        assertEquals("three", topics.get(1).getTitle());
        assertEquals("two", topics.get(2).getTitle());
    }
}