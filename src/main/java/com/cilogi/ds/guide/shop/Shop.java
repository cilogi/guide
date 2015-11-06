// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        Shop.java  (18/07/15)
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

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Shop implements Serializable {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(Shop.class);
    private static final long serialVersionUID = -4478190721183244953L;

    private String storeName;
    private String storeCurrency;
    private Set<Sku> skus;


    public static Shop fromJSONString(@NonNull String s) {
        try {
            return new GuideMapper().readValue(s, Shop.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public Shop() {
        storeCurrency = "GBP";
        skus = new LinkedHashSet<>();
    }

    public Shop(@NonNull String storeName) {
        this();
        this.storeName = storeName;
    }

    public Shop(@NonNull Shop shop) {
        this.storeName = shop.storeName;
        this.storeCurrency = shop.storeCurrency;
        this.skus = new LinkedHashSet<>(shop.skus);
    }

    public Shop addSku(@NonNull Sku sku) {
        skus.add(sku);
        return this;
    }

    public boolean addSkuIfNew(@NonNull Sku sku) {
        if (!skus.contains(sku)) {
            addSku(sku);
            return true;
        }
        return false;
    }

    public Sku getSku(@NonNull String id) {
        for (Sku sku: skus) {
            if (id.equals(sku.getId())) {
                return sku;
            }
        }
        return null;
    }

    public String toJSONString() {
        try {
            return new GuideMapper().writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
