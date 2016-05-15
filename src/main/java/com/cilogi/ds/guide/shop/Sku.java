// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        Sku.java  (18/07/15)
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


package com.cilogi.ds.guide.shop;

import com.cilogi.ds.guide.mapper.GuideMapper;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Sku implements Serializable {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(Sku.class);
    private static final long serialVersionUID = -4119412393370563146L;

    private String id;
    private String title;
    private String description;
    private SkuImage thumb;
    private SkuImage image;
    private BigDecimal unitPrice;
    private List<String> tags;

    public Sku() {
        id = "";
    }

    public Sku(@NonNull String id) {
        this.id = id;
    }

    public Sku title(String title) { this.title = title; return this; }
    public Sku description(String description) { this.description = description; return this; }
    public Sku image(SkuImage image) { this.image = image; return this; }
    public Sku thumb(SkuImage thumb) { this.thumb = thumb; return this; }
    public Sku unitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; return this; }
    public Sku tags(List<String> tags) { this.tags = tags; return this; }

    public String toJsonString() {
        try {
            return new GuideMapper().writeValueAsString(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Sku) {
            Sku oSku = (Sku)o;
            return id.equals(oSku.id);
        }
        return false;
    }

}
