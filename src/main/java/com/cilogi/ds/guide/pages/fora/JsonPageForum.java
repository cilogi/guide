// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        PageForum.java  (28/09/15)
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonPageForum implements IPageForum {
        @SuppressWarnings("unused")
        static final Logger LOG = LoggerFactory.getLogger(JsonPageForum.class);
        private static final long serialVersionUID = -8938034339745991107L;

    private String pageId;

    private List<Topic> topics;

    private Date created;

    private JsonPageForum() {
        topics = new ArrayList<>();
    }

    public JsonPageForum(@NonNull String pageId) {
        this();
        this.pageId = pageId;
        created = new Date();
    }

    public Topic getTopic(String title) {
        Topic t = new Topic(title);
        for (Topic topic : topics) {
            if (topic.equals(t)) {
                return topic;
            }
        }
        return null;
    }

    @Override @JsonIgnore
    public String getGuideName() {
        String[] sub = pageId.split("/");
        return sub[0];
    }

    @Override @JsonIgnore
    public String getPageIndex() {
        String[] sub = pageId.split("/");
        return join(sub, 1);
    }

    static String join(String[] sub, int off) {
        StringBuilder sb = new StringBuilder();
        for (int i = off; i < sub.length; i++) {
            sb.append(sub[i]);
            if (i < sub.length-1) {
                sb.append("/");
            }
        }
        return sb.toString();
    }


    public String toJSONString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (IOException e) {
            LOG.warn("Can't write PageForum: " + e.getMessage());
            return null;
        }
    }

    public boolean addTopic(@NonNull Topic topic) {
        if (!hasTopic(topic)) {
            topics.add(topic);
            return true;
        }
        return false;
    }

    private boolean hasTopic(@NonNull Topic newTopic) {
        for (Topic topic : topics) {
            if (Objects.equals(topic.getTitle(), newTopic.getTitle())) {
                return true;
            }
        }
        return false;
    }

}
