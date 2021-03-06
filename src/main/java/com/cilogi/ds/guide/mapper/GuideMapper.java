// Copyright (c) 2014 Cilogi. All Rights Reserved.
//
// File:        SpecMapper.java  (25/06/14)
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
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import org.hjson.JsonValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.vecmath.Point2d;
import java.io.IOException;
import java.math.BigDecimal;


public class GuideMapper extends ObjectMapper {
    static final Logger LOG = LoggerFactory.getLogger(GuideMapper.class);
    private static final long serialVersionUID = -7916375199391568387L;

    public GuideMapper() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(BigDecimal.class, new ToStringSerializer());

        module.addSerializer(Point2d.class, new Point2dSerializer());
        module.addDeserializer(Point2d.class, new Point2dDeserializer());

        module.addSerializer(LatLng.class, new LatLngSerializer());
        module.addDeserializer(LatLng.class, new LatLngDeserializer());

        module.addSerializer(Location.class, new LocationSerializer());
        module.addDeserializer(Location.class, new LocationDeserializer());

        module.addDeserializer(PageImage.class, new PageImageDeserializer());

        registerModule(module);
        registerModule(new GuavaModule());
        setVisibility(getVisibilityChecker().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Make sure we read stuff as Hjson.  Its not quite as efficient,
     * but makes things much more readable if data is created by hand
     * @param content  The Hjson content
     * @param valueType The class value to which we'll convert the Hjson
     * @param <T>  The type of the conversion class
     * @return  An instance of the class
     * @throws IOException  If there is a problem, the Hjson is properly formed, or we can't
     * create an instance of <code>T</code> from the Hjson data.
     */
    public <T> T readValueHjson(String content, Class<T> valueType) throws IOException {
        String hjson = JsonValue.readHjson(content).toString();
        return readValue(hjson, valueType);
    }

}
