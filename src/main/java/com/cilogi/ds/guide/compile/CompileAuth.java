// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        CompileInfo.java  (12/10/15)
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


package com.cilogi.ds.guide.compile;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Accessors(chain=true)
public class CompileAuth implements Serializable {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(CompileAuth.class);
    private static final long serialVersionUID = -1280464684850425442L;

    private String user;
    private String token;
    private String tag;

    public CompileAuth() {}

    public CompileAuth(CompileAuth info) {
        this.user = info.user;
        this.token = info.token;
        this.tag = info.tag;
    }
}
