// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        MetaUtil.java  (14/05/15)
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


package com.cilogi.util;

import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;


public class MetaUtil {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(MetaUtil.class);

    private static final String INDEX_KEY = "index";

    private MetaUtil() {}

    public static Integer getIndex(SetMultimap<String,Object> map) {
        Collection<Object> c = map.get(INDEX_KEY);
        if (c.size() == 0) {
            return null;
        } else {
            String s = c.iterator().next().toString();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                LOG.warn("Can't parse <" + s + "> as Integer index");
                return null;
            }
        }
    }

    public static void setIndex(Integer index, SetMultimap<String,Object> map) {
        map.removeAll(INDEX_KEY);
        if (index != null) {
            map.put(INDEX_KEY, Integer.toString(index));
        }
    }

    public static boolean hasTagPattern(@NonNull Multimap<String,Object> meta, @NonNull String tagPattern) {
        Collection<Object> tags = meta.get("tag");

        if (meta.containsKey(tagPattern)) {
            return true;
        }
        if (tagPattern.endsWith(":")) {
            for (Object tagObject : tags) {
                if (tagObject.toString().startsWith(tagPattern)) {
                    return true;
                }
            }
        } else if (tagPattern.startsWith(":")) {
            for (Object tagObject : tags) {
                if (tagObject.toString().endsWith(tagPattern)) {
                    return true;
                }
            }
        }
        return false;
    }
}
