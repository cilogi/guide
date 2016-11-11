// Copyright (c) 2016 Cilogi. All Rights Reserved.
//
// File:        GuideLink.java  (10/17/16)
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


package com.cilogi.ds.guide.link;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NonNull;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class GuideLink {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(GuideLink.class);

    static final Pattern EMBEDDED_LINK_PATTERN = Pattern.compile("!?\\[([^\\]]*)\\]\\s*\\(([^\\)]*)\\)");

    private final String title;
    private final GuideURN urn;

    public static List<GuideLink> guideLinks(@NonNull String text) {
        List<LinkInfo> links = links(text);
        List<GuideLink> out = Lists.newArrayList();
        for (LinkInfo link: links) {
            try {
                GuideURN guideURN = GuideURN.parse(link.getUrl());
                out.add(new GuideLink(link.getAlt(), guideURN));
            } catch (GuideURN.ParseException e) {
                LOG.warn("Can't parse " + link.getUrl() + " as link");
            }
        }
        return out;
    }

    public static List<LinkInfo> links(@NonNull String text) {
        List<LinkInfo> out = Lists.newArrayList();
        Matcher m = EMBEDDED_LINK_PATTERN.matcher(text);
        while (m.find()) {
            out.add(new LinkInfo(m.group(1), m.group(2)));
        }
        return out;
    }

    public GuideLink(@NonNull String title, @NonNull GuideURN urn) {
        this.title = title;
        this.urn = urn;
    }

    public String path() {
        return getUrn().path();
    }

    public String path(String extension) {
        return getUrn().path(extension);
    }

    @Override
    public String toString() {
        boolean isImage = urn.getType() == URNType.image;
        String imagePrefix = isImage ? "!" : "";
        return imagePrefix + "[" + getTitle() + "](" + urn.toString() + ")";
    }


    @Value
    private static class LinkInfo {
        private final String alt;
        private final String url;
        LinkInfo(String alt, String url) {
            this.alt = alt;
            this.url = url;
        }
    }
}
