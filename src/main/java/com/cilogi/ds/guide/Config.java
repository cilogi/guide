// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        Connfig.java  (03/03/15)
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


package com.cilogi.ds.guide;

import com.cilogi.ds.guide.mapper.GuideMapper;
import com.cilogi.ds.guide.mapper.LatLng;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Config implements Serializable {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(Config.class);
    private static final long serialVersionUID = 7278619937069443158L;

    public static String IMAGE_DISPLAY_COVER = "image-display:cover";
    public static String IMAGE_DISPLAY_CONTAIN = "image-display:contain";

    private String title;
    /** Description of the guide.  Goes to its meta-data. */
    private String description;
    /** The latlng of the guide, the central or start point */
    private LatLng latlng;
    /** Is this guide shared, so that others can make mixtrails */
    private boolean shared;
    /** The blogs (lists of posts, which can be published) for this guide */
    private List<String> blogs;
    /* The theme used by the guide. Only jqm at the moment */
    private String theme;
    /* The Google Analytics Key */
    private String analyticsKey;
    /* Who owns copyright */
    private String copyright;
    /* Who gets the credit */
    private String attribution;
    /* The UI look, within the theme.  Changes color schemes for jqm */
    private String uiTheme;
    /* Email address to contact */
    private String email;
    /* Tour to use by default (can be null) */
    private String defaultTour;
    /* Provide keypad access to items by index 8? */
    private boolean showKeypad;
    /* List of URLs for which you want list view */
    private List<String> listview;
    /* Permissions */
    private Permissions permissions;
    private Map<String,String> templates;
    private Compile compile;
    private FileSize filesize;

    /** The display type of a page image, modelled after the css <code>background-size</code>
     * property.
     */
    private String imageDisplay;

    public static Config fromJSON(String data) throws IOException {
        GuideMapper mapper = new GuideMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper.readValue(data, Config.class);
    }

    public Config() {
        title = "";
        description = "";
        shared = false;
        blogs = Lists.newArrayList("blogs/");
        theme = "jqm";
        analyticsKey = "";
        copyright = "";
        attribution = "";
        uiTheme = "w";
        email = "";
        showKeypad = true;
        listview = new ArrayList<>();
        permissions = new Permissions();
        templates = new HashMap<>();
        compile = new Compile();
        filesize = new FileSize();
        imageDisplay = IMAGE_DISPLAY_CONTAIN;
    }

    public Config(Config config) {
        this.title = config.title;
        this.description = config.description;
        this.shared = config.shared;
        this.latlng = config.latlng;
        this.blogs = new ArrayList<>(config.blogs);
        this.theme = config.theme;
        this.analyticsKey = config.analyticsKey;
        this.copyright = config.copyright;
        this.attribution = config.attribution;
        this.uiTheme = config.uiTheme;
        this.email = config.email;
        this.defaultTour = config.defaultTour;
        this.showKeypad = config.showKeypad;
        this.listview = new ArrayList<>(config.listview);
        this.permissions = new Permissions(config.permissions);
        this.templates = Maps.newHashMap(config.templates);
        this.compile = new Compile(config.compile);
        this.filesize = new FileSize(config.filesize);
        this.imageDisplay = config.imageDisplay;
    }

    @Data
    public static class Compile implements Serializable {
        private static final long serialVersionUID = -2183403776360154014L;

        boolean isCompress;
        boolean isOffline;

        Compile() {
            isCompress = false;
            isOffline = false;
        }

        Compile(@NonNull Compile compile) {
            isCompress = compile.isCompress;
            isOffline = compile.isOffline;
        }
    }

    @Data
    public static class FileSize implements Serializable {
        private static final long serialVersionUID = -7921068399407224169L;
        int max;
        List<String> except;

        FileSize() {
            max = 120;
            except = Lists.newArrayList("*.html", "scripts/*", "styles/*");
        }

        FileSize(FileSize fileSize) {
            max = fileSize.max;
            except = Lists.newArrayList(fileSize.except);
        }
    }

    @Data
    public static class Templates implements Serializable {
        private static final long serialVersionUID = -4309542512696962187L;

        private List<List<String>> templates;

        public Templates() {
            templates = new ArrayList<>();
        }

        public Templates(Templates t) {
            templates = Lists.newLinkedList();
            for (List<String> list : t.templates) {
                templates.add(new ArrayList<>(list));
            }
        }
    }

    @Data
    public static class Permissions implements Serializable {
        private static final long serialVersionUID = 5267728693660290062L;

        private Map<String, List<String>> permissions;

        public Permissions() {
            this.permissions = new LinkedHashMap<>();
        }

        public Permissions(Permissions p) {
            permissions = new HashMap<>();
            for (String key: p.permissions.keySet()) {
                permissions.put(key, new ArrayList<>(p.permissions.get(key)));
            }
        }

        public List<String> permittedUserForVersion(@NonNull String version) {
            List<String> permitted = permissions.get(version);
            return (permitted == null) ? new ArrayList<String>() : permitted;
        }

        public boolean isPermitted(@NonNull String user, @NonNull String version) {
            List<String> users = permittedUserForVersion(version);
            for (String email: users) {
                if (email.equalsIgnoreCase(user)) {
                    return true;
                }
            }
            return false;
        }
    }

    public String toJSONString() {
        try {
            GuideMapper mapper = new GuideMapper();
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            LOG.error("Can't convert Config " + this + " to JSON string");
            return "{}";
        }
    }
}
