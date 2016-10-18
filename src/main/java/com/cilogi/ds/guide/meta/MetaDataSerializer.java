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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.ResolvableSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class MetaDataSerializer extends JsonSerializer<MetaData> {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(MetaDataDeserializer.class);
    private static final long serialVersionUID = -5258894605875241153L;

    private final JsonSerializer<Object> defaultSerializer;

    public MetaDataSerializer(JsonSerializer<Object> defaultSerializer) {
      this.defaultSerializer = defaultSerializer;
    }

    @Override
    public void serialize(MetaData value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        //defaultSerializer.serialize(value.getData(), gen, provider);
        gen.writeObject(value.getData());
    }
}
