// Copyright (c) 2016 Cilogi. All Rights Reserved.
//
// File:        GenerateSchema.java  (9/15/16)
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

import com.cilogi.ds.guide.GuideJson;
import com.cilogi.ds.guide.meta.MetaData;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.reinert.jjschema.Attributes;
import com.github.reinert.jjschema.JsonSchemaGenerator;
import com.github.reinert.jjschema.SchemaGeneratorBuilder;
import com.github.reinert.jjschema.SchemaIgnore;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;


public class GenerateSchema {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(GenerateSchema.class);

    public GenerateSchema() {

    }

    public static void main(String[] args) {
        try {
            HashMultimap<String,Object> map = HashMultimap.create();
            JsonSchemaGenerator v4generator = SchemaGeneratorBuilder.draftV4Schema().build();
            JsonNode productSchema = v4generator.generateSchema(GuideJson.class);
            System.out.println(productSchema);
        } catch (Exception e) {
            LOG.error("oops", e);
        }
    }

    @Data
    static class Demo {
        private Opaque hidden;
        @Attributes(title = "MetaData", description = "Object MetaData")
        private HashMap<String,Object> map;
    }

    @Data
    static class Opaque {
        @SchemaIgnore
        int val;
    }
}
