// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        TestPage.java  (23/01/15)
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
import com.cilogi.ds.guide.mapper.Location;
import com.cilogi.ds.guide.meta.MetaData;
import com.cilogi.util.Digest;
import com.cilogi.util.IOUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.*;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.LinkedHashSet;

import static org.junit.Assert.assertEquals;

public class TestPage {
    @SuppressWarnings({"unused"})
    static final Logger LOG = LoggerFactory.getLogger(TestPage.class);


    public TestPage() {
    }

    @Before
    public void setUp() {

    }

    @Test
    public void testJsonSerialize() throws IOException {
        ObjectMapper mapper = new GuideMapper();
        Page page = page();
        String out = mapper.writeValueAsString(page);
        Page back = mapper.readValue(out, Page.class);
        assertEquals(back, page);
        assertEquals(page.getText(), back.getText());
    }

    @Test
    // equality ignores text
    public void testEquals() {
        Page page = page();
        Page other = page();
        other.setText("");
        other.setEtag(page.getEtag());
        assertEquals(page, other);
    }

    @Test
    public void pageWithMeta() throws IOException {
        Page page = page();
        MetaData data = new MetaData().put("key1", "value1").put("key2", "value2");
        page.setMetaData(data);
        String out = page.toJSONString();
        Page back = Page.parse(out);
        assertEquals(page, back);
    }

    @Test
    public void testSerialize() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        Page page = page();
        oos.writeObject(page);
        oos.close();
        byte[] data = os.toByteArray();
        ByteArrayInputStream is = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(is);
        Page back = (Page)ois.readObject();
        assertEquals(null, back.getText());
    }

    @Test
    public void testTags() throws IOException {
        Page page = page();
        LinkedHashSet<String> set = Sets.newLinkedHashSet(Lists.newArrayList("one", "two", "three"));
        page.addTags(set);
        String out = page.toJSONString();
        Page back = Page.parse(out);
        assertEquals(page, back);
    }

    @Test
    public void testDigest() throws IOException {
        Page page = Page.parse("{\"id\":1,\"title\":\"title\",\"guideName\":\"guideName\",\"text\":\"hi\"}");
        String out = page.toJSONString();
        Page back = Page.parse(out);
        assertEquals(page, back);
    }

    @Test
    public void testEmptyDigest() throws IOException {
        String empty = "";
        String digest = Page.computeEtag(empty);
        assertEquals("d41d8cd98f00b204e9800998ecf8427e", digest);
        assertEquals("c1a5298f939e87e8f962a5edfc206918", Digest.digestHex("Hi", Digest.Algorithm.MD5));
    }

    @Test
    public void testPageRef() {
        Page page = page();
        String id = page.getPageRef("demo-guide").toId();
        assertEquals("1", id);
    }

    @Test
    public void testParseOld() throws IOException {
        String s = IOUtil.loadStringUTF8(getClass().getResource("page.json"));
        Page page = Page.parse(s);
        assertEquals("images/page101.jpg", page.getImage());
    }

    @Test
    public void testCopy() {
        Page page = page();
        Page copy = page.copy();
        assertEquals(page, copy);
    }

    @Test
    public void testCopyFrom() {
        Page page = page();
        Page copy = new Page();
        copy.setId(page.getId());
        copy.copyFrom(page);
        assertEquals(page, copy);
    }

    private Page page() {
        MetaData map = new MetaData();
        map.put("tag", "a");
        map.put("marker", "cafe");

        Page page = new Page();
        page.setId(1);
        page.setTitle("Title");
        page.setGuideName("demo-guide");
        page.setImages(Lists.newArrayList(new PageImage("a.png"), new PageImage("b.png", "b")));
        page.setUrl("url");
        page.setLocation(new Location(1,2));
        page.setPageLinks(Lists.newArrayList(new PageLink(1), new PageLink(2)));
        page.setMetaData(map);
        page.setEtag("etag");
        page.setText("This is a long piece of text");
        return page;
    }


}