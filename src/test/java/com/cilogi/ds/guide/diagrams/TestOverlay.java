// Copyright (c) 2016 Cilogi. All Rights Reserved.
//
// File:        TestOverlay.java  (28-Nov-16)
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

import javax.vecmath.Point2d;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class TestOverlay {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(TestOverlay.class);


    public TestOverlay() {
    }

    @Before
    public void setUp() {

    }

    @Test
    public void testEmpty() {
        assertEquals("{}", new Overlay().toJSONString());
    }

    @Test
    public void testDigest() throws IOException {
        String spec = IOUtil.loadStringUTF8(getClass().getResource("sample-overlay.json"));
        Overlay overlay = Overlay.fromJSON(spec);
        assertEquals("cddf93952650cb7f43220b33050cd902", overlay.digest());  // if this fails we've changed something (and this could be bad)
    }

    @Test
    public void testCanonical() throws IOException {
        String spec = IOUtil.loadStringUTF8(getClass().getResource("sample-overlay.json"));
        Overlay overlay = Overlay.fromJSON(spec);
        String out = overlay.toJSONString();
        Overlay back = Overlay.fromJSON(out);
        String out2 = back.toJSONString();
        assertEquals(overlay, back);
        assertEquals(out2, out);
        List<ImageSpec> specs = overlay.getImages();
        assertEquals(1, specs.size());
        Locate locate = specs.get(0).getLocates().get(0);
        Point2d latlng = locate.getLatlng();
        assertEquals(new Point2d(55.8818253, -4.2936141), latlng);
    }
}