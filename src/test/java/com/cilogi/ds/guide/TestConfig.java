// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        TestConfig.java  (03/03/15)
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

import com.cilogi.util.IOUtil;
import com.cilogi.util.Pickle;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestConfig {
    static final Logger LOG = LoggerFactory.getLogger(TestConfig.class);


    public TestConfig() {
    }

    @Before
    public void setUp() {

    }

    @Test
    public void testDefault() throws IOException {
        Config config = new Config();
        String out = config.toJSONString();
        Config back = Config.fromJSON(out);
        assertEquals(config, back);
        assertTrue(config.isShared());
    }

    @Test
    public void testSerializable() throws IOException, ClassNotFoundException {
        Config config = new Config();
        byte[] data = Pickle.pickle(config);
        Config back = Pickle.unpickle(data, Config.class);
        assertEquals(back, config);
    }

    @Test
    public void testReal() throws IOException {
        Config config = Config.fromJSON(IOUtil.loadStringUTF8(getClass().getResource("demo-config.json")));
        String json = config.toJSONString();
        Config back = Config.fromJSON(json);
        assertEquals(config, back);

        Config copy = new Config(config);
        assertEquals(copy, config);
    }

    @Test
    public void testRealPickle() throws IOException, ClassNotFoundException {
        Config config = Config.fromJSON(IOUtil.loadStringUTF8(getClass().getResource("demo-config.json")));
        byte[] data = Pickle.pickle(config);
        Config back = Pickle.unpickle(data, Config.class);
        assertEquals(config, back);

        Config copy = new Config(config);
        assertEquals(copy, config);
    }
}