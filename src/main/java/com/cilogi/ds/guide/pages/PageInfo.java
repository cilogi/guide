// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        PageInfo.java  (01/10/15)
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class PageInfo {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(PageInfo.class);

    private Page page;
    private String digest;

    private PageInfo() {}

    public PageInfo(@NonNull Page page) {
        this.page = page.copy();
        this.digest = page.digest();
    }


}
