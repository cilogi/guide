// Copyright (c) 2016 Cilogi. All Rights Reserved.
//
// File:        GuideLinkTranslator.java  (09-Nov-16)
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

import com.cilogi.ds.guide.filter.ITextFilter;
import com.cilogi.ds.guide.pages.PageLink;
import com.cilogi.util.path.PathBetween;
import com.cilogi.util.path.PathUtil;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GuideLinkTranslator implements ITextFilter {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(GuideLinkTranslator.class);

    private static final Pattern EMBEDDED_LINK = GuideLink.EMBEDDED_LINK_PATTERN;

    private final String resourcePath;
    private final Map<String,GuideURN> paths;

    public GuideLinkTranslator(@NonNull String resourcePath, Map<String,GuideURN> paths) {
        this.resourcePath = resourcePath;
        this.paths = paths;
    }

    public String translateRelative(@NonNull String path) {
        String abs = translateAbsolute(path);
        return isAbsolute(abs) ? abs : new PathBetween(resourcePath, abs).compute();
    }


    public String translateAbsolute(@NonNull String path) {
        GuideURN urn = GuideURN.parse(path);
        switch (urn.getType()) {
            case url:
                if (isAbsolute(urn.getId()))  {
                    return urn.getId();
                } else {
                    String absPath = PathUtil.changeRelative(resourcePath, urn.getId());
                    GuideURN absURN = paths.get(makeCanonical(absPath));
                    return (absURN == null) ? absPath : absURN.path();
                }
            default:
                return urn.path();
        }
    }

    public String filter(String text) {
        StringBuilder out = new StringBuilder();
        int current = 0;
        Matcher m = EMBEDDED_LINK.matcher(text);
        while (m.find()) {
            int start = m.start(2);
            int end = m.end(2);
            String link = m.group(2);
            out.append(text.substring(current, start));

            GuideURN url = GuideURN.parse(link, URNType.url);
            if (url.getType() == URNType.url) {
                if (!isAbsolute(url.getId())) {
                    String fullPath = PathUtil.changeRelative(resourcePath, url.getId());
                    GuideURN altURN = paths.get(fullPath);
                    if (altURN != null) {
                        url = altURN;
                    }
                }
            }
            String newPath = url.path();

            current = end;
        }
        out.append(text.substring(current));
        return out.toString();
    }

    private static boolean isAbsolute(@NonNull String url) {
        if (url == null || "".equals(url)) {
            return false;
        }
        return url.charAt(0) == '/' || url.matches("^\\w+://.+$");
    }

    public static final String makeCanonical(@NonNull String path) {
        String lc = path.toLowerCase();
        return (lc.endsWith(".md") || lc.endsWith(".html")) ? PathUtil.changeExtension(path, "") : path;
    }
}
