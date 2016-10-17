// Copyright (c) 2016 Cilogi. All Rights Reserved.
//
// File:        LinkType.java  (10/17/16)
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


package com.cilogi.ds.guide.link;

public enum URNType {
    audio("media/audios/", false),
    image("media/images/", false),
    listing("listings/", true),
    map("diagrams/", true),
    page( "contents/pages/", true),
    tour("tours/", true),
    url(null, false);

    private String path;
    private boolean stripExtension;

    URNType(String path, boolean stripExtension) {
        this.path = path;
        this.stripExtension = stripExtension;
    }
    public String path() {
        return path;
    }
    boolean stripExtension() {
        return stripExtension;
    }
}
