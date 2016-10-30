// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        PageRef.java  (19/08/15)
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


package com.cilogi.ds.guide.tours;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageRef implements Serializable {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(PageRef.class);
    private static final long serialVersionUID = 2102787885889629855L;

    private String guideName;
    private int pageIndex;

    private PageRef() {
        guideName = "";
        pageIndex = -1;
    }

    public PageRef(@NonNull String address) {
        String[] sub = address.trim().split("/");
        if (sub.length == 1 || sub.length == 2) {
            boolean hasGuideName = sub.length == 2;
            this.guideName = hasGuideName ? sub[0] : "";
            try {
                this.pageIndex = Integer.parseInt(sub[hasGuideName ? 1 : 0]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Address " + address + " is not formatted as a page address, " +
                        sub[hasGuideName ? 1 : 0] + " is not an integer");
            }
        } else {
            guideName = "";
            pageIndex = -1;
        }
    }

    public PageRef(int pageIndex) {
        this("", pageIndex);
    }

    public PageRef(@NonNull String guideName, int pageIndex) {
        this.guideName = guideName;
        this.pageIndex = pageIndex;
    }


    @JsonIgnore
    public boolean isCompatibleGuide(@NonNull String name) {
        return "".equals(guideName) || guideName.equals(name);
    }

    @JsonIgnore
    public boolean isValid() {
        return getPageIndex() != -1;
    }

    @JsonIgnore
    public boolean isExternal() {
        return !"".equals(getGuideName());
    }

    public String toId() {
        return "".equals(guideName) ? Integer.toString(pageIndex) : guideName + "/" + Integer.toString(pageIndex);
    }

    @Override
    public String toString() {
        return toId();
    }
}
