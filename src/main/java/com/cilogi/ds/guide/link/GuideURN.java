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

import lombok.NonNull;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Value
public class GuideURN {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(GuideURN.class);

    private static final String REVISION_PREFIX = "revision=";
    private static final String COLON_CODED = "%3A";

    private final String guideName;
    private final URNType type;
    private final String id;
    private final String revision;

    public static GuideURN parse(@NonNull String urn) {
        return parse(urn, URNType.page);
    }

    public static GuideURN parse(@NonNull String urn, @NonNull URNType deflt) {
        String revision = null;

        if (isHTTP(urn)) {
            return new GuideURN(URNType.url, urn);
        }

        String[] sub = urn.split(":");
        if (sub.length == 1) {
            if (deflt == URNType.page) {
                try {
                    Integer.parseInt(urn);
                    return new GuideURN(deflt, urn);
                } catch (NumberFormatException e) {
                    return new GuideURN(URNType.url, urn);
                }
            } else {
                return new GuideURN(deflt, urn);
            }
        } else {
            String last = sub[sub.length-1];
            if (last.startsWith(REVISION_PREFIX)) {
                revision = last.substring(REVISION_PREFIX.length());
            }
            int length = (revision == null) ? sub.length : sub.length-1;
            if (length == 3) {
                // guideName:type:id
                try {
                    URNType type = URNType.valueOf(sub[1]);
                    return new GuideURN(sub[0], type, sub[2], revision);
                } catch (IllegalArgumentException e) {
                    return new GuideURN(sub[0], deflt, sub[2], revision);
                }
            } else if (length == 2) {
                // type:id
                try {
                    URNType type = URNType.valueOf(sub[0]);
                    return new GuideURN(null, type, sub[1], revision);
                } catch (IllegalArgumentException e) {
                    throw new ParseException("Can't parse " + urn + ": illegal link type <" + sub[0] + ">");
                }
            } else if (length == 1) {
                // deflt:id
                return new GuideURN(null, deflt, urn, revision);
            } else {
                throw new ParseException("Can't parse " + urn + ": not enough components");
            }
        }
    }

    public GuideURN(@NonNull URNType type, @NonNull String id) {
        this(null, type, id, null);
    }

    public GuideURN(String guideName, @NonNull URNType type, @NonNull String id, String revision) {
        this.guideName = guideName;
        this.type = type;
        this.id = id.replaceAll(COLON_CODED, ":");
        this.revision = revision;
    }

    @Override
    public String toString() {
        if (getType() == URNType.url && isHTTP(getId())) {
            return getId();
        } else {
            String guidePart = (guideName == null || guideName.length() == 0)
                    ? ""
                    : guideName + ":";
            String revisionPart = (revision == null || revision.length() == 0)
                    ? ""
                    : ":revision=" + revision;
            return guidePart + getType().name() + ":" + escapedID() + revisionPart;
        }
    }

    private String escapedID() {
        String id = getId();
        return id.replaceAll(":", COLON_CODED);
    }

    private static boolean isHTTP(@NonNull String url) {
        String lc = url.toLowerCase();
        return (lc.startsWith("http://") || lc.startsWith("https://"));
    }

    public static class ParseException extends RuntimeException {
        private static final long serialVersionUID = -3450289795662171886L;

        ParseException(String s) {
            super(s);
        }
    }

}
