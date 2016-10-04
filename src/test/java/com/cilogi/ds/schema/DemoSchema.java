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

import com.cilogi.ds.guide.Config;
import com.cilogi.ds.guide.GuideJson;
import com.cilogi.ds.guide.listings.Listing;
import com.cilogi.ds.guide.mapper.Location;
import com.cilogi.ds.guide.pages.PageImage;
import com.cilogi.ds.guide.shop.Shop;
import com.cilogi.ds.guide.tours.Tour;
import com.cilogi.geometry.LatLng;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JacksonUtils;
import com.github.reinert.jjschema.JsonSchemaGenerator;
import com.github.reinert.jjschema.SchemaGeneratorBuilder;
import com.github.reinert.jjschema.Util;
import com.google.common.collect.Multimap;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.vecmath.Point2d;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Trying out stuff to see what we can generate */
@SuppressWarnings({"unused"})
public class DemoSchema {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(DemoSchema.class);

    public DemoSchema() {

    }

    public static void main(String[] args) {
        File root = new File("C:\\work\\projects\\cilogi\\libs\\guide\\src\\main\\resources\\schemata");
        Class[] classes = {
                Config.class,
                Tour.class,
                Listing.class,
                Shop.class,
                GuideJson.class
        };
        Map<Class,String> refs = new HashMap<>();
        refs.put(Point2d.class, "/schemata/Point2d.json");
        refs.put(Location.class, "/schemata/Location.json");
        refs.put(PageImage.class, "/schemata/PageImage.json");
        try {
            JsonSchemaGenerator v4generator = SchemaGeneratorBuilder.draftV4Schema()
                    .referenceMap(refs)
                    .build();
            for (Class clazz : classes) {
                Util.saveClass(v4generator, clazz, root);
                JsonNode productSchema = v4generator.generateSchema(clazz);
                System.out.println(JacksonUtils.prettyPrint(productSchema));
            }
        } catch (Exception e) {
            LOG.error("oops", e);
        }
    }
}
