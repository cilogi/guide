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

import com.cilogi.ds.guide.Config;
import com.cilogi.ds.guide.GuideJson;
import com.cilogi.ds.guide.listings.Listing;
import com.cilogi.ds.guide.mapper.Location;
import com.cilogi.ds.guide.pages.PageImage;
import com.cilogi.ds.guide.shop.Shop;
import com.cilogi.ds.guide.tours.Tour;
import com.github.reinert.jjschema.JsonSchemaGenerator;
import com.github.reinert.jjschema.SchemaGeneratorBuilder;
import com.github.reinert.jjschema.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.vecmath.Point2d;
import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class GenerateSchema {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(GenerateSchema.class);

    private static final String DEFAULT_ROOT = "C:\\work\\projects\\cilogi\\libs\\guide\\src\\main\\resources\\schemata";

    private final String rootFile;

    public GenerateSchema() {
        this(DEFAULT_ROOT);
    }

    public GenerateSchema(String rootFile) {
        this.rootFile = rootFile;
    }

    public void generate() {
        File root = new File(rootFile);
        Class[] classes = {
                Config.class,
                Tour.class,
                Listing.class,
                Shop.class,
                GuideJson.class
        };
        Map<Class, String> refs = new HashMap<>();
        refs.put(Point2d.class, "/schemata/Point2d.json");
        refs.put(Location.class, "/schemata/Location.json");
        refs.put(PageImage.class, "/schemata/PageImage.json");
        try {
            JsonSchemaGenerator v4generator = SchemaGeneratorBuilder.draftV4Schema()
                    .referenceMap(refs)
                    .build();
            for (Class clazz : classes) {
                Util.saveClass(v4generator, clazz, root);
            }
        } catch (Exception e) {
            LOG.error("oops", e);
        }

    }

    public static void main(String[] args) {
        try {
            new GenerateSchema().generate();
        } catch (Exception e) {
            LOG.error("oops", e);
        }
    }
}
