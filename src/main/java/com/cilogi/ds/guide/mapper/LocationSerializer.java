// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        LocationSerializer.java  (20/10/15)
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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


class LocationSerializer  extends JsonSerializer<Location> {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(LocationSerializer.class);

    LocationSerializer() {

    }

    public void serialize(Location location, JsonGenerator gen, SerializerProvider provider)
            throws IOException {
        gen.writeStartArray();
        if (location.getImage() != null) {
            gen.writeString(location.getImage());
        }
        gen.writeNumber(location.getX());
        gen.writeNumber(location.getY());
        gen.writeEndArray();
    }
}
