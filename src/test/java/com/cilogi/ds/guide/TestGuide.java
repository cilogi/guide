// Copyright (c) 2014 Cilogi. All Rights Reserved.
//
// File:        TestGuide.java  (03/12/14)
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


package com.cilogi.ds.guide;


import com.cilogi.ds.guide.tours.PageRef;
import com.cilogi.ds.guide.tours.Tour;
import com.cilogi.util.IOUtil;
import com.cilogi.util.Pickle;
import com.google.common.collect.Sets;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.Assert.*;

public class TestGuide {
    @SuppressWarnings({"unused"})
    static final Logger LOG = LoggerFactory.getLogger(TestGuide.class);


    public TestGuide() {
    }

    @BeforeClass
    public static void setUpBeforeClass() {

        // Reset the Factory so that all translators work properly.
        //ObjectifyService.setFactory(new ObjectifyFactory());
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
        //AsyncCacheFilter.complete();
    }


    @Test
    public void testPublicJSONString() {
        IGuide guide = new GuideJson("test", "tim@timniblett.net");
        String s = guide.toJSONString(true);
        assertFalse(s.contains("secret"));
    }

    @Test
    public void testCopy() {
        IGuide guide = new GuideJson("test", "tim@timniblett.net");
        guide.setServingVersion("99");
        guide.setVersions(Sets.newHashSet("one", "two", "99"));

        IGuide copy = new GuideJson(guide);
        assertEquals(copy, guide);
    }

    @Test
    public void testReal() throws IOException {
        IGuide guide = GuideJson.fromJSON(IOUtil.loadStringUTF8(getClass().getResource("/demo-guide.json")));
        assertEquals("demo-guide", guide.getName());
        String json = guide.toJSONString();
        IGuide back = GuideJson.fromJSON(json);
        assertEquals(guide, back);
    }

    @Test
    public void testRealSerial() throws IOException, ClassNotFoundException {
        IGuide guide = GuideJson.fromJSON(IOUtil.loadStringUTF8(getClass().getResource("/demo-guide.json")));
        assertEquals("demo-guide", guide.getName());
        byte[] data = Pickle.pickle(guide);
        IGuide back = Pickle.unpickle(data, GuideJson.class);
        assertEquals(guide, back);
    }

    @Test
    public void testSerializable() throws IOException, ClassNotFoundException {
        IGuide guide = new GuideJson("test", "test@example.com");
        byte[] data = Pickle.pickle(guide);
        IGuide back = Pickle.unpickle(data, GuideJson.class);
        assertEquals(back, guide);
    }

    @Test
    public void testFail() throws IOException, ClassNotFoundException {
        GuideJson guide = GuideJson.fromJSON(IOUtil.loadStringUTF8(getClass().getResource("demo-fail.json")));
        assertEquals("demo-guide", guide.getName());
    }

    @Test
    public void testExportTour() throws IOException, ClassNotFoundException {
        GuideJson guide = GuideJson.fromJSON(IOUtil.loadStringUTF8(getClass().getResource("/demo-guide.json")));
        assertEquals("demo-guide", guide.getName());
        Tour tour = guide.findTour("botanics-trail");
        assertNotNull(tour);
        Tour export = guide.exportTour("botanics-trail");
        assertEquals(4, export.getStops().size());
        assertEquals("demo-guide/204", export.getStops().get(0).getId());
    }

    @Test
    public void testImportTour() throws IOException, ClassNotFoundException {
        GuideJson guide = GuideJson.fromJSON(IOUtil.loadStringUTF8(getClass().getResource("/demo-guide.json")));
        assertEquals("demo-guide", guide.getName());
        Tour tour = guide.findTour("botanics-trail");
        Tour copy = new Tour(tour);
        copy.setId("copy");
        copy.getStops().get(0).setPageRef(new PageRef("other_guide", 204));
        copy.getStops().get(1).setPageRef(new PageRef("demo-guide", 1));

        Tour imported = guide.importTour(copy);
        assertEquals(2, guide.getTours().size());
        assertEquals(new PageRef("other_guide", 204), imported.getStops().get(0).getPageRef());
        assertEquals(new PageRef("", 1), imported.getStops().get(1).getPageRef());
    }
}