// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        LatLngDeserializer.java  (26/02/15)
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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.NumericNode;

import java.io.IOException;


class LatLngDeserializer extends JsonDeserializer<LatLng> {

    LatLngDeserializer() {}

    @Override
    public LatLng deserialize(JsonParser jp, DeserializationContext _any)
            throws IOException {
        TreeNode node = jp.getCodec().readTree(jp);
        double lat = ((NumericNode) node.get(0)).doubleValue();
        double lng = ((NumericNode) node.get(1)).doubleValue();
        return new LatLng(lat, lng);
    }
}
