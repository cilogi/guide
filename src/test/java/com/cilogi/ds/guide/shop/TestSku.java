// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        TestSku.java  (06/11/15)
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

import org.junit.Before;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class TestSku {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(TestSku.class);


    public TestSku() {
    }

    @Before
    public void setUp() {

    }

    @Test
    public void testEquals() {
        Set<Sku> skus = new LinkedHashSet<>();
        Sku a = new Sku("a");
        a.setTitle("aTitle");
        a.setDescription("aDescription");
        a.setUnitPrice(new BigDecimal(1.5));
        skus.add(a);
        assertTrue(skus.contains(new Sku("a")));
        assertTrue(a.equals(new Sku("a")));
    }
}