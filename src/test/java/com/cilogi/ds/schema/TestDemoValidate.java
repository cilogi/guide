// Copyright (c) 2016 Cilogi. All Rights Reserved.
//
// File:        TestDemoValidate.java  (9/29/16)
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
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.load.configuration.LoadingConfiguration;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.core.load.uri.URITranslatorConfiguration;
import org.junit.Before;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.*;

public class TestDemoValidate {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(TestDemoValidate.class);

    private static final String NAMESPACE = "resource:/com/cilogi/ds/schema/";

    public TestDemoValidate() {
    }

    @Before
    public void setUp() {

    }

    @Test
    public void testValidate() throws ProcessingException, IOException {
        final URITranslatorConfiguration translatorCfg
                = URITranslatorConfiguration.newBuilder()
                .setNamespace(NAMESPACE).freeze();
        final LoadingConfiguration cfg = LoadingConfiguration.newBuilder()
                .setURITranslatorConfiguration(translatorCfg).freeze();

        final JsonSchemaFactory factory = JsonSchemaFactory.newBuilder()
                .setLoadingConfiguration(cfg).freeze();

        final JsonSchema schema = factory.getJsonSchema("major-schema.json");
        LOG.info("schema loaded OK");

        URL url = getClass().getResource("major-sample.json");
        JsonNode sample = JsonLoader.fromURL(getClass().getResource("major-sample.json"));
        LOG.info("sample loaded");

        ProcessingReport report = schema.validate(sample);
        System.out.println(report);
    }
}