// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        PagePost.java  (28/09/15)
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
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;


@Data
@Accessors(chain=true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class JsonPost implements IPost {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(JsonPost.class);

    public static JsonPost fromJSON(String s) throws IOException {
        return new ObjectMapper().readValue(s, JsonPost.class);
    }

    private String pageId;

    private String topic;

    private String text;

    private String author;

    private Date date;

    private boolean deleted;

    private JsonPost() {}

    public JsonPost(@NonNull String pageId, @NonNull String topic,
                    @NonNull String text, @NonNull String author) {
        this.pageId = pageId;
        this.topic = topic;
        this.text = text;
        this.author = author;
        date = new Date();
        deleted = false;
    }
}
