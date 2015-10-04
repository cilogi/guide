// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        Topic.java  (02/10/15)
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


package com.cilogi.ds.guide.pages.fora;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain=true)
public class Topic implements Serializable {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(Topic.class);
    private static final long serialVersionUID = 8563305975876947630L;

    @Getter
    private String title;

    @Getter
    private List<IPost> posts;

    private Topic() {
        posts = new ArrayList<>();
    }

    public Topic(String title) {
        this();
        this.title = title;
    }

    public void addPost(IPost post) {
        posts.add(post);
    }

    @Override
    public int hashCode() {
        return (title == null) ? 0 : title.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Topic) {
            Topic to = (Topic)o;
            return Objects.equals(to.getTitle(), getTitle());
        }
        return false;
    }
}
