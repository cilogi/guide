// Copyright (c) 2016 Cilogi. All Rights Reserved.
//
// File:        WikiPages.java  (9/7/16)
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

import com.cilogi.ds.guide.mapper.GuideMapper;
import com.cilogi.ds.guide.pages.PageInfo;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
public class WikiPages {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(WikiPages.class);

    private List<WikiPageInfo> pageList;

    public WikiPageInfo parse(String s) throws IOException {
        GuideMapper mapper = new GuideMapper();
        return mapper.readValueHjson(s, WikiPageInfo.class);
    }

    public WikiPages() {
        pageList = new ArrayList<>();
    }

    public int size() {
        return pageList.size();
    }

    public boolean isValidPages() {
        for (WikiPageInfo info : pageList) {
            if (!info.isValidPage()) {
                return false;
            }
        }
        return true;
    }
}
