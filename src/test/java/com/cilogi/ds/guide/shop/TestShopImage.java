// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        TestShopImage.java  (19/07/15)
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


package com.cilogi.ds.guide.shop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.Assert.*;

public class TestShopImage {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(TestShopImage.class);

    public TestShopImage() {}

    @Before
    public void setUp() {}

    @Test
    public void testEmpty() throws IOException {
        SkuImage image = new SkuImage("hello");
        String s = new ObjectMapper().writeValueAsString(image);
        assertEquals("{\"url\":\"hello\"}", s);
    }
}