// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        IGuide.java  (22/03/15)
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

import com.cilogi.ds.guide.diagrams.Diagrams;
import com.cilogi.ds.guide.filter.ITextFilter;
import com.cilogi.ds.guide.filter.ITextFilterable;
import com.cilogi.ds.guide.listings.Listing;
import com.cilogi.ds.guide.media.GuideAudio;
import com.cilogi.ds.guide.media.GuideImage;
import com.cilogi.ds.guide.menus.Menu;
import com.cilogi.ds.guide.pages.Page;
import com.cilogi.ds.guide.shop.Shop;
import com.cilogi.ds.guide.tours.Tour;
import com.cilogi.ds.guide.wiki.WikiPageInfo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IGuide extends Serializable, ITextFilterable {
    public String MEDIA_IMAGE_PATH = "media/images/";

    public String getName();

    public String getGuideSpecVersion();

    public Config getConfig();

    public void setConfig(Config config);
    public String getConfigName();

    public String getTitle();
    public void setTitle(String title);

    public String getDescription();
    public void setDescription(String description);

    public List<Page> getPages();
    public void setPages(List<Page> pages);

    public Map<String,String> getPageDigests();

    public Diagrams getDiagrams();
    public void setDiagrams(Diagrams maps);

    public List<Tour> getTours();
    public void setTours(List<Tour> tours);

    public Set<GuideImage> getImages();
    public void setImages(Set<GuideImage> guideImages);

    public Set<GuideAudio> getAudioClips();
    public void setAudioClips(Set<GuideAudio> guideAudioClips);


    public Shop getShop();
    public void setShop(Shop shop);

    public boolean isValidState();

    public List<Listing> getListings();
    public void setListings(List<Listing> listings);

    public List<WikiPageInfo> getWikiPages();
    public void setWikiPages(List<WikiPageInfo> wikiPages);

    public Map<String,Menu> getMenus();
    public void setMenus(Map<String,Menu> guideMenus);

    public Page findPage(int pageId);

    public void updatePage(Page page);

    @Override
    public void filter(ITextFilter filter);

    public String toJSONString();

}
