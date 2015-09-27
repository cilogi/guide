// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        LogEvent.java  (07/05/15)
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

package com.cilogi.ds.guide.log;

import com.cilogi.ds.guide.mapper.GuideMapper;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LogEvent implements Serializable {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(com.cilogi.ds.guide.log.LogEvent.class);
    private static final long serialVersionUID = -4046797567004576654L;


    public static LogEvent fromJSON(String s) throws IOException {
        GuideMapper mapper = new GuideMapper();
        return mapper.readValue(s, LogEvent.class);
    }

    public enum Type {
        info, warning, error, heartbeat, stop
    }

    private Type type;

    private long time;

    private String message;

    private String uuid;

    private LogEvent() {}

    public LogEvent(@NonNull Type type, @NonNull String message) {
        this.type = type;
        this.message = message;
        this.time = new Date().getTime();
    }

    public String toJSONString() {
        try {
            GuideMapper mapper = new GuideMapper();
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            LOG.warn("Can't convert LogEvent " + this + " to JSON string");
            return "{}";
        }
    }
}
