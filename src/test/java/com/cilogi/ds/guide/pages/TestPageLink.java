// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        TestPageLink.java  (06/05/15)
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


package com.cilogi.ds.guide.pages;

import org.junit.Before;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Matcher;

import static org.junit.Assert.*;

public class TestPageLink {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(TestPageLink.class);


    public TestPageLink() {
    }

    @Before
    public void setUp() {

    }

    @Test
    public void testPath() {
        PageLink link = new PageLink(PageLink.Type.map, "map/map1");
        String path = PageLink.path(link, "json");
        assertEquals("diagrams/map1.json", path);
    }

    @Test
    public void testGallery() {
        PageLink link = new PageLink(PageLink.Type.gallery, "fred");
        String path = PageLink.path(link, "json");
        assertEquals("galleries/fred.json", path);
    }

    @Test
    public void testXGuideLink() {
        String text = "Hello\n[link]({botanics}/pages/100)\nGoodbye\n";
        List<PageLink> links = PageLink.pageLinks(text);
        assertEquals(1, links.size());
        assertEquals("botanics", links.get(0).getGuideName());
    }

    @Test
    public void testParseLink() {
        String link = "{botanics}/pages/100";
        Matcher m = PageLink.EXTERNAL_GUIDE_PATTERN.matcher(link);
        assertTrue(m.matches());
        assertEquals("botanics", m.group(1));
        assertEquals("pages/100", m.group(2));
    }

    @Test
    public void testParseLinkSpace() {
        String link = " {botanics}/pages/100  ";
        Matcher m = PageLink.EXTERNAL_GUIDE_PATTERN.matcher(link);
        assertTrue(m.matches());
        assertEquals("botanics", m.group(1));
        assertEquals("pages/100", m.group(2).trim());
    }

    @Test
    public void testParseLinkNoGuide() {
        String link = "pages/100  ";
        Matcher m = PageLink.EXTERNAL_GUIDE_PATTERN.matcher(link);
        assertTrue(m.matches());
        assertEquals(null, m.group(1));
        assertEquals("pages/100", m.group(2).trim());
    }

    @Test
    public void testIPageLinksImage() {
        String text = "Hello\n\n[link](media/images/image.png)\n\nGoodbye";
        List<PageLink> links = PageLink.iPageLinks(text);
        assertEquals(1, links.size());

        assertEquals(PageLink.Type.image, links.get(0).getType());
        assertEquals("image.png", links.get(0).getValue());
    }

    @Test
    public void testIPageLinksAudio() {
        String text = "Hello\n\n[link](media/audios/audio.mp3)\n\nGoodbye";
        List<PageLink> links = PageLink.iPageLinks(text);
        assertEquals(1, links.size());

        assertEquals(PageLink.Type.audio, links.get(0).getType());
        assertEquals("audio.mp3", links.get(0).getValue());
    }

    @Test
    public void testIPageLinksPage() {
        String text = "Hello\n\n[link](contents/pages/100.html)\n\nGoodbye";
        List<PageLink> links = PageLink.iPageLinks(text);
        assertEquals(1, links.size());

        assertEquals(PageLink.Type.page, links.get(0).getType());
        assertEquals("100", links.get(0).getValue());
    }

    @Test
    public void testIPageLinksListing() {
        String text = "Hello\n\n[link](listings/listing.html)\n\nGoodbye";
        List<PageLink> links = PageLink.iPageLinks(text);
        assertEquals(1, links.size());

        assertEquals(PageLink.Type.listing, links.get(0).getType());
        assertEquals("listing", links.get(0).getValue());
    }

    @Test
    public void testIPageLinksTour() {
        String text = "Hello\n\n[link](tours/tour.html)\n\nGoodbye";
        List<PageLink> links = PageLink.iPageLinks(text);
        assertEquals(1, links.size());

        assertEquals(PageLink.Type.tour, links.get(0).getType());
        assertEquals("tour", links.get(0).getValue());
    }

    @Test
    public void testIPageLinksGallery() {
        String text = "Hello\n\n[link](galleries/gallery.html)\n\nGoodbye";
        List<PageLink> links = PageLink.iPageLinks(text);
        assertEquals(1, links.size());

        assertEquals(PageLink.Type.gallery, links.get(0).getType());
        assertEquals("gallery", links.get(0).getValue());
    }

    @Test
    public void testIPageLinksURL() {
        String text = "Hello\n\n[link](contents/trail/file.html)\n\nGoodbye";
        List<PageLink> links = PageLink.iPageLinks(text);
        assertEquals(1, links.size());

        assertEquals(PageLink.Type.url, links.get(0).getType());
        assertEquals("contents/trail/file.html", links.get(0).getValue());
    }
}