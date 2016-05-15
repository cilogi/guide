// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        PageImageDeserializer.java  (08/11/15)
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


package com.cilogi.ds.guide.mapper;

import com.cilogi.ds.guide.pages.PageImage;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.NumericNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/** A page image can either be a string or an object with a string
 *  and a pair of
 */
public class PageImageDeserializer extends JsonDeserializer<PageImage> {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(PageImageDeserializer.class);
    private static final long serialVersionUID = -1934993567686395338L;


    PageImageDeserializer() {}

    @Override
    public PageImage deserialize(JsonParser jp, DeserializationContext ctx)
            throws IOException {
        TreeNode node = jp.getCodec().readTree(jp);
        if (node.isValueNode()) {
            String src = ((JsonNode) node).asText();
            return new PageImage(src, null);
        } else {
            String src = ((JsonNode) node.get("src")).asText(); // must ecist or its an error
            JsonNode altNode = (JsonNode)node.get("alt");  // can be omitted
            String alt = null;
            if (altNode != null) {
                alt = altNode.asText();
            }
            return new PageImage(src, alt);
        }
    }
}
