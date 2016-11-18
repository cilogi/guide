// Copyright (c) 2016 Cilogi. All Rights Reserved.
//
// File:        Menu.java  (17-Nov-16)
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


package com.cilogi.ds.guide.menus;

import com.cilogi.ds.guide.mapper.GuideMapper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)

@Data
public class Menu implements Serializable {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(Menu.class);
    private static final long serialVersionUID = 5666874012675336559L;

    public static Menu parse(String s) throws IOException {
        GuideMapper mapper = new GuideMapper();
        return mapper.readValueHjson(s, Menu.class);
    }


    private String title;
    private List<MenuLink> links;

    public Menu() {
        links = new ArrayList<>();
    }

    public String toJSONString() {
        ObjectMapper mapper = new GuideMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (IOException e) {
            return "";
        }
    }
}
