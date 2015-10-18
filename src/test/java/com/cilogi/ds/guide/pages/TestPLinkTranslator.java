// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        TestPLinkTranslator.java  (30/04/15)
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

import com.cilogi.util.path.PathBetween;
import org.junit.Before;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class TestPLinkTranslator {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(TestPLinkTranslator.class);


    public TestPLinkTranslator() {
    }

    @Before
    public void setUp() {

    }

    @Test
    public void testPaths() {
        String path = "contents/trail/foo.html";
        String href = "images/foo.jpg";
        assertEquals("contents/trail/images/foo.jpg", new PathBetween(path,href).computeFullPathForTo());
    }

    @Test
    public void testTranslate() {
        PLinkTranslator translator = new PLinkTranslator(new Oracle());
        String path = "contents/trail/foo.html";
        assertEquals("../pages/1.html", translator.translate("pages/1", path));

        assertEquals("../../media/images/foo.jpg", translator.translate("foo.jpg", path));
        assertEquals("../../media/images/foo.jpg", translator.translate("images/foo.jpg", path));

        assertEquals("../../media/audios/foo.mp3", translator.translate("foo.mp3", path));
        assertEquals("../../media/audios/foo.mp3", translator.translate("audios/foo.mp3", path));

        assertEquals("../../listings/listing1.html", translator.translate("listings/listing1", path));

        assertEquals("../../tours/tour1.html", translator.translate("tours/tour1", path));

        assertEquals("../../diagrams/map1.html", translator.translate("maps/map1", path));

    }

    @Test
    public void testNoPath() {
        PLinkTranslator translator = new PLinkTranslator();
        try {
            assertEquals("../../ref1", translator.translate("../../ref1", "hello.txt"));
        } catch (Exception e) {
            fail("Should be null exception");
        }
    }

    @Test
    public void testTranslateHash() {
        PLinkTranslator translator = new PLinkTranslator(new Oracle());
        String path = "contents/trail/foo.html";
        assertEquals("../../diagrams/map1.html#35", translator.translate("maps/map1#35", path));
    }

    @Test
    public void testTranslateQuery() {
        PLinkTranslator translator = new PLinkTranslator(new Oracle());
        String path = "contents/trail/foo.html";
        assertEquals("../../diagrams/map1.html?id=35", translator.translate("maps/map1?id=35", path));

    }

    @Test
    public void testTranslateIgnore() {
        PLinkTranslator translator = new PLinkTranslator(new Oracle());
        String path = "contents/trail/foo.html";
        assertEquals("#main", translator.translate("#main", path));
    }

    @Test
    public void testReal() {
        PLinkTranslator translator = new PLinkTranslator(new Oracle());
        String path = "tours/botanics-trail.html";
        String href = "../contents/trail/sundial.html?tour=botanics-trail";
        assertEquals("../contents/trail/sundial.html?tour=botanics-trail", translator.translate(href, path));
    }

    @Test
    public void testMix() {
        PLinkTranslator tr = new PLinkTranslator();
        String out = tr.translate("../../media/images/cain.jpg", "contents/mix/pages/5.json");
        assertEquals("../../../media/images/cain.jpg", out);
    }

    @Test
    public void testImage() {
        PLinkTranslator translator = new PLinkTranslator(new Oracle());
        String out = translator.translate("fred.jpg", "contents/pages/5.json");
        assertEquals("../../media/images/fred.jpg", out);
    }

    @Test
    public void testImageImages() {
        PLinkTranslator translator = new PLinkTranslator(new Oracle());
        String out = translator.translate("images/fred.jpg", "contents/pages/5.json");
        assertEquals("../../media/images/fred.jpg", out);
    }

    private static class Oracle implements PLinkOracle {
        public boolean pathExists(String path) {
            return false;
        }
    }
}