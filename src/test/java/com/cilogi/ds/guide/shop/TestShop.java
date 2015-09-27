// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        TestStore.java  (18/07/15)
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

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class TestShop {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(TestShop.class);


    public TestShop() {
    }

    @Before
    public void setUp() {

    }

    @Test
    public void testShop() {
        Shop shop = new Shop("botanics");

        Sku sku = new Sku();
        sku.setId("100");
        sku.setTitle("title");
        sku.setDescription("description");
        sku.setImage(new SkuImage("image"));
        sku.setThumb(new SkuImage("thumb"));
        sku.setUnitPrice(new BigDecimal("5.97"));

        Set<Sku> skus = new LinkedHashSet<>(Arrays.asList(sku));

        shop.setSkus(skus);

        String s = shop.toJSONString();
        Shop back = Shop.fromJSONString(s);
        assertEquals(shop, back);
    }
}