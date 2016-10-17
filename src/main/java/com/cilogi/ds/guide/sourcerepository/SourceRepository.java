// Copyright (c) 2014 Cilogi. All Rights Reserved.
//
// File:        Link.java  (03/12/14)
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


package com.cilogi.ds.guide.sourcerepository;

import com.cilogi.ds.guide.mapper.GuideMapper;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Information linking a guide to an external data source containing input data
 * for the guide.  The information includes the type of link and the token or address
 * needed to access the guide.
 */
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class SourceRepository implements ISourceRepository {
    @SuppressWarnings({"unused"})
    static final Logger LOG = LoggerFactory.getLogger(SourceRepository.class);
    private static final long serialVersionUID = -7959265416318563235L;

    /**
     * The guide for this link
     */
    @Getter
    private String guideName;

    @Getter
    private SourceRepositoryType linkType;

    /**
     * The Dropbox token (maybe others later).  This is private and so
     * needs to be explicitly accessed and is NOT serialized to JSON
     */
    @Getter
    private String token;

    /**
     * User ID for Dropbox
     */
    @Getter
    private Long userID;

    /**
     * HTTPS URL for Git repo
     */
    @Getter
    private String url;

    /**
     * Email of the user who created this link
     */
    @Getter
    private String userName;

    public static SourceRepository fromJSONString(@NonNull String json) {
        try {
            return new GuideMapper().readValueHjson(json, SourceRepository.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings({"unused"})
    public static SourceRepositoryType gitType(@NonNull String url) {
        if (validateGithub(url) == null) {
            return SourceRepositoryType.Github;
        } else if (validateBitbucket(url) == null) {
            return SourceRepositoryType.Bitbucket;
        } else {
            return null;
        }
    }

    private SourceRepository() {}

    public SourceRepository(SourceRepository link) {
        this.guideName = link.guideName;
        this.linkType = link.linkType;
        this.token = link.token;
        this.userID = link.userID;
        this.url = link.url;
        this.userName = link.userName;
    }

    public SourceRepository(@NonNull SourceRepositoryType linkType, @NonNull String userName) {
        this();
        this.linkType = linkType;
        this.userName = userName.trim();
    }

    @SuppressWarnings({"unused"})
    public boolean isValid() {
        return validate() == null;
    }

    public SourceRepository guideName(String guideName) {
        this.guideName = guideName; return this;
    }

    public SourceRepository token(String token) {
        this.token = token; return this;
    }

    public SourceRepository userID(long userID) {
        this.userID = userID; return this;
    }

    public SourceRepository url(String url) {
        this.url = url; return this;
    }

    public SourceRepository safe() {
        SourceRepository copy = new SourceRepository(this);
        copy.userID = (userID == null || userID == 0L) ? 0L : -1L;
        copy.token = (token == null) ? null : "**set**";
        return copy;
    }

    /**
     * Check whether a token is OK
     * @return An error if not, null if it is
     */
    public String validate() {
        switch (linkType) {
            case Dropbox: return (token == null) ? "No token" : null;
            case Github: return (url == null) ? "URL has not been set" : validateGithub(url);
            case Bitbucket: return (url == null) ? "URL has not been set" : validateBitbucket(url);
            default: return null;
        }
    }

    public String toJSONString() {
        try {
            return new GuideMapper().writeValueAsString(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String validateGithub(@NonNull String url) {
        Pattern pattern = Pattern.compile("^https://github.com/([^/]+)/(.+)\\.git$");
        Matcher matcher = pattern.matcher(url);
        return matcher.matches()
                ?  null
                : url + " does not match the pattern https://github.com/{userName}/{guide}.git";
    }

    private static String validateBitbucket(@NonNull String url) {
        Pattern pattern = Pattern.compile("^https://([^/]+)/([^/]+)/(.+)\\.git$");
        Matcher matcher = pattern.matcher(url);
        return matcher.matches()
                ? null
                : url + " does not match the pattern https://{userName}@bitbucket.org/{userName}/{guide}.git";
    }
}
