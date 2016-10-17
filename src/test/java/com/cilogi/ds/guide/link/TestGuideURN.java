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

import org.junit.Before;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class TestGuideURN {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(TestGuideURN.class);


    public TestGuideURN() {
    }

    @Before
    public void setUp() {

    }

    @Test
    public void testParseId() {
        String id="501";
        GuideURN link = GuideURN.parse(id);
        assertEquals("501", link.getId());
        assertEquals(URNType.page, link.getType());
        assertNull(link.getGuideName());
        assertNull(link.getRevision());
    }

    @Test
    public void testParseType() {
        String id="image:foo.jpg";
        GuideURN link = GuideURN.parse(id);
        assertEquals("foo.jpg", link.getId());
        assertEquals(URNType.image, link.getType());
        assertNull(link.getGuideName());
        assertNull(link.getRevision());
    }

    @Test
    public void testParseGuide() {
        String id="otherGuide:image:foo.jpg";
        GuideURN link = GuideURN.parse(id);
        assertEquals("foo.jpg", link.getId());
        assertEquals(URNType.image, link.getType());
        assertEquals("otherGuide", link.getGuideName());
        assertNull(link.getRevision());
    }

    @Test
    public void testParseAll() {
        String id="otherGuide:image:foo.jpg:revision=one";
        GuideURN link = GuideURN.parse(id);
        assertEquals("foo.jpg", link.getId());
        assertEquals(URNType.image, link.getType());
        assertEquals("otherGuide", link.getGuideName());
        assertEquals("one", link.getRevision());
    }

    @Test
    public void testRemotePage() {
        String id = "guide::501";
        GuideURN link = GuideURN.parse(id);
        assertEquals("501", link.getId());
        assertEquals(URNType.page, link.getType());
        assertEquals("guide", link.getGuideName());
        assertNull(link.getRevision());
    }

    @Test
    public void testWikiVersion() {
        String id = "wikipedia::Oak:revision=12345";
        GuideURN link = GuideURN.parse(id);
        assertEquals("Oak", link.getId());
        assertEquals(URNType.page, link.getType());
        assertEquals("wikipedia", link.getGuideName());
        assertEquals("12345", link.getRevision());
    }

    @Test
    public void testString() {
        GuideURN link = GuideURN.parse("153");
        assertEquals("page:153", link.toString());
    }

    @Test
    public void testStringImage() {
        GuideURN link = GuideURN.parse("image:153.jpg");
        assertEquals("image:153.jpg", link.toString());
    }

    @Test
    public void testURLPlain() {
        String id = "http://en.wikipedia.org/wiki/Oak";
        GuideURN link = GuideURN.parse(id);
        assertEquals(id, link.toString());
    }

    @Test
    public void testURLImage() {
        String id = "http://en.wikipedia.org/wiki/Oak.jpg";
        GuideURN link = new GuideURN(URNType.image, id);
        assertEquals("image:http%3A//en.wikipedia.org/wiki/Oak.jpg", link.toString());
        GuideURN back = GuideURN.parse(link.toString());
        assertEquals(link, back);
    }
}