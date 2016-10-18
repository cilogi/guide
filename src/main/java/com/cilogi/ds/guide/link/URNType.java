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
    audio("media/audios/", null),
    image("media/images/", null),
    thumb("media/thumbs", null),
    listing("listings/", "html"),
    map("diagrams/", "html"),
    page( "contents/pages/", "html"),
    tour("tours/", "html"),
    url(null, null);

    private String path;
    private String defaultExtension;

    URNType(String path, String defaultExtension) {
        this.path = path;
        this.defaultExtension = defaultExtension;
    }

    public String path() {
        return path;
    }

    String defaultExtension() {
        return defaultExtension;
    }
}
