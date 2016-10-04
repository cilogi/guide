// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        TestPageImage.java  (10/11/15)
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

import com.cilogi.ds.guide.mapper.GuideMapper;
import com.cilogi.util.Pickle;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestPageImage {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(TestPageImage.class);


    public TestPageImage() {
    }

    @Before
    public void setUp() {

    }

    @Test
    @SuppressWarnings({"unchecked"})
    public void testReadNormally() throws IOException, ClassNotFoundException {
        ArrayList<PageImage> pages = Lists.newArrayList(new PageImage("src0", "alt0"), new PageImage("src1", "alt1"));

        byte[] data = Pickle.pickle(pages);
        ArrayList<PageImage> back = Pickle.unpickle(data, ArrayList.class);
        assertEquals(back, pages);

    }

    @Test
    public void testReadCompact() throws IOException {
        final String src = "image/one.jpg";
        final String srcJson = "\""+src+"\"";
        PageImage image = new GuideMapper().readValue(srcJson, PageImage.class);
        assertEquals(src, image.getSrc());
        assertNull(image.getAlt());
    }

    @Test
    public void testReadCompactArray() throws IOException {
        final String src = "image/one.jpg";
        final String srcArray = "[\""+src+"\"]";
        List<PageImage> images = new GuideMapper().readValue(srcArray, new TypeReference<List<PageImage>>() {});
        assertEquals(src, images.get(0).getSrc());
        assertNull(images.get(0).getAlt());
    }
}