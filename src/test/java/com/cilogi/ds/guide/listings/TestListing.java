// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        TestListing.java  (07/04/15)
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


package com.cilogi.ds.guide.listings;

import com.cilogi.ds.guide.GuideJson;
import com.cilogi.ds.guide.IGuide;
import com.cilogi.ds.guide.mapper.GuideMapper;
import com.cilogi.ds.guide.pages.Page;
import com.cilogi.util.Pickle;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TestListing {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(TestListing.class);


    public TestListing() {
    }

    @Before
    public void setUp() {

    }

    @Test
    public void testMatch() {
        IGuide guide = new GuideJson("guide", "tim");
        guide.setPages(Lists.newArrayList(
                new Page("guide", 1).addTags(ImmutableSet.of("one", "car:mercedes", "red:color")),
                new Page("guide", 2).addTags(ImmutableSet.of("two", "car:audi", "blue:color")),
                new Page("guide", 3).addTags(ImmutableSet.of("one", "car:jaguar", "green:color"))
        ));
        assertEquals(2, new Listing("id", ImmutableSet.of("one")).init(guide).size());
        assertEquals(3, new Listing("id", ImmutableSet.of("car:")).init(guide).size());
        assertEquals(3, new Listing("id", ImmutableSet.of(":color")).init(guide).size());
        assertEquals(1, new Listing("id", ImmutableSet.of("car:audi")).init(guide).size());
        assertEquals(1, new Listing("id", ImmutableSet.of("blue:")).init(guide).size());
    }

    @Test
    public void testJson() throws IOException {
        Listing listing = new Listing("id", ImmutableSet.of("one", "two", "three"));
        listing.setTitle("title");
        listing.setDescription("description");
        listing.setPageIds(ImmutableSet.of(1,2,3));
        ObjectMapper mapper = new GuideMapper();
        String json = mapper.writeValueAsString(listing);
        assertEquals(json, "{\"id\":\"id\",\"type\":\"text\",\"title\":\"title\",\"description\":\"description\",\"tags\":[\"one\",\"two\",\"three\"],\"metaData\":{}}");
    }

    @Test
    public void testPickle() throws IOException, ClassNotFoundException {
        Listing listing = new Listing("id", ImmutableSet.of("one", "two", "three"));
        listing.setTitle("title");
        listing.setDescription("description");
        listing.setPageIds(Sets.newHashSet(1,2,3));
        byte[] data = Pickle.pickle(listing);
        Listing back = Pickle.unpickle(data, Listing.class);
        assertEquals(listing, back); // transient properties ignored
    }

    @Test
    public void testIndex() throws IOException {
        Listing listing = new Listing("id", ImmutableSet.of("one"));
        listing.setTitle("title");
        listing.setDescription("description");
        listing.setPageIds(ImmutableSet.of(1,2,3));
        listing.setIndex(100);
        ObjectMapper mapper = new GuideMapper();
        String json = mapper.writeValueAsString(listing);
        assertEquals(json, "{\"id\":\"id\",\"type\":\"text\",\"title\":\"title\",\"description\":\"description\",\"tags\":[\"one\"],\"metaData\":{\"index\":[\"100\"]}}");
    }

    @Test
    public void parse() throws IOException {
        String json = "{\"id\":\"id\",\"index\":\"100\",\"type\":\"text\",\"title\":\"title\",\"description\":\"description\",\"tags\":[\"one\"]}";
        Listing listing = new GuideMapper().readValue(json, Listing.class);
        assertEquals(null, listing.getIndex());
    }
}