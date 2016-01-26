// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        TourStop.java  (12/03/15)
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

import com.cilogi.ds.guide.mapper.Location;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TourStop implements Serializable {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(TourStop.class);
    private static final long serialVersionUID = 7365232721304149894L;

    private static final String NO_REF_GUIDE = "stop";  // the name of the guide used as a null placeholder

    private String id;       // reference to the page, containing guide and index
    private String intro; // extra material to add at the front of a page, can be null
    private String title; // alternative title for the stop, can be null
    private Location location; // if the page ref is local, so there is no location
    private transient String path;  // path of the resource for this stop, can be null

    private TourStop() {}

    public TourStop(@NonNull TourStop s) {
        this.id = s.id;
        this.intro = s.intro;
        this.title = s.title;
        this.path = s.path;
    }

    public TourStop(@NonNull String id, String intro) {
        this.id = id;
        this.intro = intro;
    }

    public void makePublic(@NonNull String guideName) {
        PageRef pageRef = getPageRef();
        if (!pageRef.isExternal()) {
            PageRef publicRef = new PageRef(guideName, getPageIndex());
            setId(publicRef.toId());
        }
    }

    @JsonIgnore
    public int getPageIndex() {
        return getPageRef().getPageIndex();
    }

    @JsonIgnore
    public String getGuideName() {
        return getPageRef().getGuideName();
    }


    @JsonIgnore
    public PageRef getPageRef() {
        return new PageRef(id);
    }

    @JsonIgnore
    public void setPageRef(@NonNull PageRef ref) {
        setId(ref.toId());
    }

    @JsonIgnore
    public boolean isNoRef() {
        return NO_REF_GUIDE.equals(getGuideName());
    }
}
