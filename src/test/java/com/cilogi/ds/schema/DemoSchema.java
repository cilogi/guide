// Copyright (c) 2016 Cilogi. All Rights Reserved.
//
// File:        DemoSchema.java  (9/15/16)
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


package com.cilogi.ds.schema;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.reinert.jjschema.JsonSchemaGenerator;
import com.github.reinert.jjschema.SchemaGeneratorBuilder;
import com.google.common.collect.Multimap;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/** Trying out stuff to see what we can generate */
@SuppressWarnings({"unused"})
public class DemoSchema {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(DemoSchema.class);

    public DemoSchema() {

    }

    public static void main(String[] args) {
        try {
            JsonSchemaGenerator v4generator = SchemaGeneratorBuilder.draftV4Schema().build();
            JsonNode productSchema = v4generator.generateSchema(TypedMultiMap.class);
            System.out.println(productSchema);

        } catch (Exception e) {
            LOG.error("oops", e);
        }
    }

    @Data
    static class TypedMap {
        Map<String,String> map;
    }

    @Data
    static class TypedMultiMap {
        Multimap<String,Object> map;
    }
}
