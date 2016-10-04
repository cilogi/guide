// Copyright (c) 2016 Cilogi. All Rights Reserved.
//
// File:        TestDollarRefGeneration.java  (9/30/16)
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
import com.github.fge.jackson.JacksonUtils;
import com.github.reinert.jjschema.*;
import lombok.Data;
import org.junit.Before;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class TestDollarRefGeneration {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(TestDollarRefGeneration.class);


    public TestDollarRefGeneration() {
    }

    @Before
    public void setUp() {

    }

    @Test
    public void testDollarRef() {
        try {
            JsonSchemaGenerator v4generator = SchemaGeneratorBuilder.draftV4Schema().build();
            JsonNode productSchema = v4generator.generateSchema(DollarRef.class);
            System.out.println(JacksonUtils.prettyPrint(productSchema));
            Util.saveClass(v4generator, DollarRef.class, new File("C:\\tmp"));
        } catch (Exception e) {
            LOG.error("oops", e);
        }
    }

    @Data
    //@SchemaFileName("DollarRef")
    static class DollarRef {
        @JsonReference("reference.json/#")
        List<Opaque> refs;
    }

    @Data
    static class Opaque {
        int val;
    }
}