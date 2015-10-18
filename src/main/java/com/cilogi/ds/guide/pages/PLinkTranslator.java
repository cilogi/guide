// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        PLinkTranslator.java  (30/04/15)
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

import com.cilogi.util.path.PathBetween;
import com.google.common.base.Preconditions;
import lombok.Data;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PLinkTranslator {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(PLinkTranslator.class);

    private final static Pattern HASH_PATTERN = Pattern.compile("(^.+)(#[^#]+)$");
    private final static Pattern QUERY_PATTERN = Pattern.compile("(^.+)(\\?.+)$");
    private final PLinkOracle oracle;

    @SuppressWarnings({"unused"})
    public PLinkTranslator() {
        this(new PLinkOracle() {
            public boolean pathExists(String path) {
                return false;
            }
        });
    }

    public PLinkTranslator(PLinkOracle oracle) {
        this.oracle = oracle;
    }

    public String translate(@NonNull String hrefWithQueryOrHash, @NonNull String sourcePath) {
        Preconditions.checkArgument(!"".equals(sourcePath), "source path can't be empty");
        if (isIgnoredLink(hrefWithQueryOrHash)) {
            return hrefWithQueryOrHash;
        }

        Split split = new Split(hrefWithQueryOrHash);
        String href = split.getHref();
        String hash = split.getHash();

        String path = new PathBetween(sourcePath, href).computeFullPathForTo();

        if (oracle.pathExists(path) || path == null) {
            // leave the reference alone if it actually points to something
            return href + hash;
        } else {
            PageLink.Type type = PageLink.type(href);
            PageLink link = new PageLink(type, path);
            String dstPath = PageLink.path(link);
            return PathBetween.compute(sourcePath, dstPath) + hash;
        }
    }

    private boolean isIgnoredLink(String href) {
        return href.startsWith("/")
                || href.startsWith("#")
                || href.startsWith("http:")
                || href.startsWith("https:");
    }

    @Data
    private static class Split {
        private String href;
        private String hash;

        Split(@NonNull String href) {
            Matcher queryMatch = QUERY_PATTERN.matcher(href);
            Matcher hashMatch = HASH_PATTERN.matcher(href);
            if (queryMatch.matches()) {
                this.href = queryMatch.group(1);
                this.hash = queryMatch.group(2);
            } else if (hashMatch.matches()) {
                this.href = hashMatch.group(1);
                this.hash = hashMatch.group(2);
            } else {
                this.href = href;
                this.hash = "";
            }
        }
    }
}
