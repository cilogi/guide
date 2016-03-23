// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        PageImage.java  (08/11/15)
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


package com.cilogi.ds.guide.pages;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class PageImage implements Serializable {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(PageImage.class);

    private String src;
    private String alt;

    private PageImage() {}

    public PageImage(@NonNull String src) {
        this(src, null);
    }

    /**
     * Constructor.
     * @param src  Non null image src
     * @param alt  Image title, can be null
     */
    public PageImage(@NonNull String src, String alt) {
        this.src = src;
        this.alt = alt;
    }
}
