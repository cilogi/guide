// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        PageLink.java  (25/03/15)
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


package com.cilogi.ds.guide.pages;

import com.cilogi.util.MimeTypes;
import com.cilogi.util.path.PathUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.cilogi.ds.guide.pages.PageLink.Type.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class PageLink implements Serializable {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(PageLink.class);
    private static final long serialVersionUID = -4942800222362467736L;

    static final Pattern EMBEDDED_LINK_PATTERN = Pattern.compile("!?\\[([^\\]]*)\\]\\s*\\(([^\\)]*)\\)");
    static final Pattern EXTERNAL_GUIDE_PATTERN = Pattern.compile("\\s*(?:\\{([^\\}]+)\\}/)?(.+)\\s*");

    public enum Type {
        audio("audios/", "media/audios/", false),
        image("images/", "media/images/", false),
        listing("listings/", "listings/", true),
        map("maps/", "diagrams/", true),
        page("pages/", "contents/pages/", true),
        thumb("thumbs/", "media/thumbs/", false),
        tour("tours/", "tours/", true),
        gallery("galleries/", "galleries/", true),
        url("", null, false);

        private String prefix;
        private String path;
        private boolean stripExtension;
        Type(String prefix, String path, boolean stripExtension) {
            this.prefix = prefix;
            this.path = path;
            this.stripExtension = stripExtension;
        }
        public String prefix() {
            return prefix;
        }
        public String path() {
            return path;
        }
        boolean stripExtension() {
            return stripExtension;
        }
    };



    public static Pattern embeddedLinkPattern() {
        return EMBEDDED_LINK_PATTERN;
    }

    public static List<PageLink> pageLinks(@NonNull String text) {
        List<LinkInfo> links = links(text);
        List<PageLink> out = Lists.newArrayList();
        for (LinkInfo link: links) {
            PageLink pageLink = text2link(link.getUrl());
            if (pageLink != null) {
                if (pageLink.type == Type.url) {
                    pageLink.setTitle(link.getAlt());
                }
                out.add(pageLink);
            } else {
                LOG.warn("Can't parse " + link + " as a page link");
            }
        }
        return out;
    }

    private static PageLink text2link(String link) {
        Matcher m = EXTERNAL_GUIDE_PATTERN.matcher(link);
        if (m.matches()) {
            String guideName = m.group(1);
            String local = m.group(2);
            return new PageLink(type(local), local).setGuideName(guideName);
        } else {
            return null;
        }
    }


    public static List<PageLink> iPageLinks(@NonNull String text) {
        List<LinkInfo> links = links(text);
        List<PageLink> out = Lists.newArrayList();
        for (LinkInfo link: links) {
            String url = link.getUrl();
            String name = PathUtil.name(url);
            if (MimeTypes.isImageMimeType(MimeTypes.getMimeTypeFromPath(url))) {
                PageLink pageLink = new PageLink(image, name);
                pageLink.setTitle(link.getAlt());
                out.add(pageLink);
            } else if (MimeTypes.isAudioMimeType(MimeTypes.getMimeTypeFromPath(url))) {
                PageLink pageLink = new PageLink(audio, name);
                pageLink.setTitle(link.getAlt());
                out.add(pageLink);
            } else {
                Matcher m = EXTERNAL_GUIDE_PATTERN.matcher(url);
                boolean ok = m.matches();
                int count = m.groupCount();
                String guideName = m.group(1);
                String local = m.group(2);
                Type type = iPath(local);

                String value;
                if (type == Type.url) {
                    value = local;
                } else {
                    value = type.stripExtension() ? PathUtil.changeExtension(name, "") : name;
                }
                PageLink pageLink = new PageLink(new PageLink(type, value));
                out.add(pageLink.setGuideName(guideName).setTitle(link.getAlt()));
            }
        }
        return out;
    }

    public static List<LinkInfo> links(@NonNull String text) {
        List<LinkInfo> out = Lists.newArrayList();
        Matcher m = EMBEDDED_LINK_PATTERN.matcher(text);
        while (m.find()) {
            out.add(new LinkInfo(m.group(1), m.group(2)));
        }
        return out;
    }

    public static Type type(@NonNull String link) {
        if (MimeTypes.isImageMimeType(MimeTypes.getMimeTypeFromPath(link))) {
            return image;
        } else if (MimeTypes.isAudioMimeType(MimeTypes.getMimeTypeFromPath(link))) {
            return audio;
        } else {
            for (Type type : Type.values()) {
                if (type == url) {
                    continue;
                }
                String prefix = type.prefix();
                if (link.startsWith(prefix)) {
                    return type;
                }
            }
            return url;
        }
    }

    public static String path(@NonNull PageLink pageLink) {
        return path(pageLink, "html");
    }

    public static String path(@NonNull PageLink pageLink, String extension) {
        Type type = pageLink.getType();
        String link = pageLink.getValue();
        if (PathUtil.isExternalURL(link)) {
            return link;
        } else {
            switch (type) {
                case audio:    return type.path() + name(link);
                case image:    return type.path() + name(link);
                case listing:  return type.path() + clean(link, extension);
                case map:      return type.path() + clean(link, extension);
                case page:     return type.path() + clean(link, extension);
                case thumb:    return type.path() + name(link);
                case tour:     return type.path() + clean(link, extension);
                case gallery:  return type.path() + clean(link, extension);
                case url:
                default:      return link;
            }
        }
    }

    public static Type iPath(@NonNull String path) {
        for (Type type : Type.values()) {
            if (type.path() != null && path.startsWith(type.path())) {
                return type;
            }
        }
        return Type.url;
    }

    public static String path(@NonNull String link) {
        Type type = type(link);
        return path(new PageLink(type, link));
    }


    private static String clean(String link, String extension) {
        return changeExtension(name(link), extension);
    }

    private String guideName; // null means current guide
    private Type type;
    private String title;
    private String value;

    private PageLink() {}

    public PageLink(int pageIndex) {
        this(Type.page, Integer.toString(pageIndex));
    }

    public PageLink(@NonNull Type type, @NonNull String value) {
        this.type = type;
        this.value = value;
    }

    public PageLink(@NonNull PageLink link) {
        this.guideName = link.guideName;
        this.type = link.type;
        this.value = link.value;
    }

    public PageLink setGuideName(String guideName) {
        this.guideName = guideName;
        return this;
    }

    @JsonIgnore
    public boolean isExternalGuide() {
        return guideName != null;
    }

    private static String name(String path) {
        int index = path.lastIndexOf("/");
        return (index == -1) ? path : path.substring(index + 1);
    }

    private static String changeExtension(String path, String extension) {
        int idx = path.lastIndexOf(".");
        if (idx == -1) {
            return path + "." + extension;
        } else {
            return path.substring(0, idx) + "." + extension;
        }
    }

    public PageLink setTitle(String title) {
        this.title = title;
        return this;
    }


    @Data
    private static class LinkInfo {
        private String alt;
        private String url;
        LinkInfo() {}
        LinkInfo(String alt, String url) {
            this.alt = alt;
            this.url = url;
        }
    }
}
