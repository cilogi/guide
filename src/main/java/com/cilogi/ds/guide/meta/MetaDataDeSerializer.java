// Copyright (c) 2016 Cilogi. All Rights Reserved.
//
// File:        MetaDataSerializer.java  (9/22/16)
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


package com.cilogi.ds.guide.meta;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.google.common.collect.HashMultimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class MetaDataDeserializer extends StdDeserializer<MetaData> implements ResolvableDeserializer {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(MetaDataDeserializer.class);
    private static final long serialVersionUID = -5258894605875241153L;

    private final JsonDeserializer<?> defaultDeserializer;

    public MetaDataDeserializer(JsonDeserializer<?> defaultDeserializer) {
      super(MetaData.class);
      this.defaultDeserializer = defaultDeserializer;
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public MetaData deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        HashMultimap<String,Object> map = (HashMultimap<String,Object>)jp.readValueAs(HashMultimap.class);
        return new MetaData(map);
    }

    // for some reason you have to implement ResolvableDeserializer when modifying BeanDeserializer
    // otherwise deserializing throws JsonMappingException??
    @Override
    public void resolve(DeserializationContext ctxt) throws JsonMappingException {
      ((ResolvableDeserializer) defaultDeserializer).resolve(ctxt);
    }

}
