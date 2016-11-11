// Copyright (c) 2016 Cilogi. All Rights Reserved.
//
// File:        GuideSerializerModifier.java  (9/22/16)
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

import com.cilogi.ds.guide.meta.MetaData;
import com.cilogi.ds.guide.meta.MetaDataSerializer;
import com.cilogi.ds.guide.tours.TourStop;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GuideSerializerModifier extends BeanSerializerModifier {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(GuideSerializerModifier.class);


    @Override
    @SuppressWarnings({"unchecked"})
    public JsonSerializer<?> modifySerializer(SerializationConfig config, BeanDescription beanDesc, JsonSerializer<?> serializer) {
        if (beanDesc.getBeanClass() == MetaData.class) {
            return new MetaDataSerializer((JsonSerializer<Object>) serializer);
        } else if (false && beanDesc.getBeanClass() == TourStop.class) {
            return new TourStopSerializer((JsonSerializer<Object>)serializer);
        }
        return serializer;
    }
}
