// Copyright (c) 2014 Cilogi. All Rights Reserved.
//
// File:        TestLink.java  (03/12/14)
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

import com.cilogi.ds.guide.sourcerepository.SourceRepository;
import com.cilogi.ds.guide.sourcerepository.SourceRepositoryType;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestLink {
    static final Logger LOG = LoggerFactory.getLogger(TestLink.class);


    public TestLink() {
    }

    @Before
    public void setUp() {

    }

    @Test
    public void testGithub() {
        SourceRepository link = new SourceRepository(SourceRepositoryType.Github, "tim.niblett@cilogi.com")
                .url("https://github.com/cilogi/gaeshiro.git");
        String ok = link.validate();
        assertEquals(null, ok);
    }

    @Test
    public void testBitbucket() {
        SourceRepository link = new SourceRepository(SourceRepositoryType.Bitbucket, "tim@timniblett.net")
                .url("https://timniblett@bitbucket.org/timniblett/xstatic.git");
        String ok = link.validate();
        assertEquals(null, ok);
    }

    @Test
    public void testGithubFail() {
        SourceRepository link = new SourceRepository(SourceRepositoryType.Github, "tim.niblett@cilogi.com")
                .url("https://github.org/cilogi/gaeshiro.git");
        String ok = link.validate();
        assertNotEquals(null, ok);
    }
}