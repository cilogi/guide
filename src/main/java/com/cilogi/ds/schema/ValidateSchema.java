// Copyright (c) 2016 Cilogi. All Rights Reserved.
//
// File:        ValidateSchema.java  (10/3/16)
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
import com.github.fge.jsonschema.core.load.uri.URITranslatorConfiguration;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.google.common.base.Charsets;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Validate schemata for Guides, and other components which can be stand-alone in a resource store.
 * <p></p>The other components are currently
 * <ul>
 *     <li>Config.</li>
 *     <li>Listing.</li>
 *     <li>Shop.</li>
 *     <li>Tour.</li>
 * </ul>
 *
 */
public class ValidateSchema {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(ValidateSchema.class);

    private static final String NAMESPACE = "resource:/schemata/";


    public enum Component {
        GuideJson, Config, Listing, Shop, Tour
    }

    private final JsonSchema schema;

    public ValidateSchema(@NonNull Component component) throws ProcessingException {
        final URITranslatorConfiguration translatorCfg = URITranslatorConfiguration.newBuilder()
                .setNamespace(NAMESPACE).freeze();
        final LoadingConfiguration cfg = LoadingConfiguration.newBuilder()
                .setURITranslatorConfiguration(translatorCfg).freeze();
        final JsonSchemaFactory factory = JsonSchemaFactory.newBuilder()
                .setLoadingConfiguration(cfg).freeze();
        schema = factory.getJsonSchema(component.name() + ".json");

    }

    public ProcessingReport validate(@NonNull byte[] data) throws IOException, ProcessingException {
        String s = new String(data, Charsets.UTF_8);
        JsonNode sample = JsonLoader.fromString(s);
        return schema.validate(sample);
    }
}
