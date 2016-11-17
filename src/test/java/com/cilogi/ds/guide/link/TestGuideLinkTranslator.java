// Copyright (c) 2016 Cilogi. All Rights Reserved.
//
// File:        TestGuideLinkTranslator.java  (16-Nov-16)
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

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static org.junit.Assert.*;

public class TestGuideLinkTranslator {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(TestGuideLinkTranslator.class);

    private static final Map<String,GuideURN> paths = ImmutableMap.of(
            "contents/trail/kibble", new GuideURN(URNType.page, "5"),
            "contents/trail/images/hopkirk.jpg", new GuideURN(URNType.image, "hopkirk.jpg")
    );

    public TestGuideLinkTranslator() {
    }


    @Test
    public void testTranslateStringTyped() {
        GuideLinkTranslator translator = new GuideLinkTranslator("contents/trail/hopkirk.md", paths);
        assertEquals("media/images/image.jpg", translator.translateAbsolute("image:image.jpg"));
        assertEquals("media/audios/audio.mp3", translator.translateAbsolute("audio:audio.mp3"));
        assertEquals("tours/botanics-trail.html", translator.translateAbsolute("tour:botanics-trail"));
        assertEquals("listings/botanics-trail.html", translator.translateAbsolute("listing:botanics-trail"));
        assertEquals("contents/pages/5.html", translator.translateAbsolute("page:5"));
    }

    /** Rather than type something like <code>page:5</code> as a link you can just link to the file
     *  containing the page.  This should get translated
     */
    @Test
    public void testTranslateStringLinks() {
        GuideLinkTranslator translator = new GuideLinkTranslator("contents/trail/hopkirk.md", paths);
        assertEquals("media/images/image.jpg", translator.translateAbsolute("image:image.jpg"));
        assertEquals("media/audios/audio.mp3", translator.translateAbsolute("audio:audio.mp3"));
        assertEquals("tours/botanics-trail.html", translator.translateAbsolute("tour:botanics-trail"));
        assertEquals("listings/botanics-trail.html", translator.translateAbsolute("listing:botanics-trail"));
        assertEquals("contents/pages/5.html", translator.translateAbsolute("page:5"));

    }

    @Test
    public void testLocalLinks() {
        GuideLinkTranslator translator = new GuideLinkTranslator("contents/trail/hopkirk.md", paths);

        assertEquals("media/images/hopkirk.jpg", translator.translateAbsolute("images/hopkirk.jpg"));
        assertEquals("contents/pages/5.html", translator.translateAbsolute("kibble.html"));

        assertEquals("../../media/images/hopkirk.jpg", translator.translateRelative("images/hopkirk.jpg"));
        assertEquals("../pages/5.html", translator.translateRelative("kibble.html"));

    }

}