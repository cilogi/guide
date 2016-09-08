// Copyright (c) 2016 Cilogi. All Rights Reserved.
//
// File:        WikiPageInfo.java  (9/7/16)
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


package com.cilogi.ds.guide.wiki;

import com.cilogi.geometry.LatLng;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown=true)
public class WikiPageInfo {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(WikiPageInfo.class);

    private int index;
    private int wikiPageIndex;
    private String name;
    private String title;
    private LatLng location;
    private List<String> tags;

    public static List<WikiPageInfo> getJSON(String s) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(s, new TypeReference<List<WikiPageInfo>>() {});
    }

    public WikiPageInfo() {
        tags = new ArrayList<>();
    }

    public String pageId() {
        if (wikiPageIndex > 0) {
            return Integer.toString(getWikiPageIndex(), 10);
        } else {
            return getName();
        }
    }

    public boolean isValidPage() {
        return (getName() != null || getWikiPageIndex() > 0) && getIndex() > 0;
    }

    public String toJSONString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (IOException e) {
            return "{}";
        }
    }
}
