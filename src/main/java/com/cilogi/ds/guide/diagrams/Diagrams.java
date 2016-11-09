// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        Maps.java  (23/01/15)
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


package com.cilogi.ds.guide.diagrams;

import com.cilogi.ds.guide.ITextFilter;
import com.cilogi.ds.guide.ITextFilterable;
import com.cilogi.ds.guide.mapper.GuideMapper;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NonNull;
import org.hjson.JsonValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Diagrams implements Serializable, ITextFilterable {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(Diagrams.class);
    private static final long serialVersionUID = -8478735908728862084L;

    private List<Marker> markers;
    private List<Diagram> diagrams;


    public static Diagrams parse(String s) throws IOException {
        GuideMapper mapper = new GuideMapper();
        return mapper.readValueHjson(s, Diagrams.class);
    }

    public Diagrams() {
        markers = Lists.newArrayList(new Marker("default"));
        diagrams = Lists.newArrayList();
    }

    public List<Diagram> listed() {
        List<Diagram> out = new ArrayList<>(diagrams.size());
        for (Diagram diagram: diagrams) {
            if (diagram.isListed()) {
                out.add(diagram);
            }
        }
        return out;
    }

    public Diagrams(@NonNull Diagrams diagrams) {
        this();
        this.markers = Lists.newArrayList(diagrams.getMarkers());
        this.diagrams = Lists.newArrayList(diagrams.getDiagrams());
    }

    public Diagram findDiagram(@NonNull String name) {
        for (Diagram diagram : diagrams) {
            if (name.equals(diagram.getName())) {
                return diagram;
            }
        }
        return null;
    }

    @SuppressWarnings({"unused"})
    public Diagram findDiagramWithMarkers(@NonNull String name) {
        Diagram map = findDiagram(name);
        Diagram copy = null;
        if (map != null) {
            copy = map.copy();
            copy.setMarkers(map.getMarkers());
        }
        return copy;
    }



    @SuppressWarnings({"unused"})
    public void updateDiagram(@NonNull Diagram diagram) {
        String name = diagram.getName();
        Diagram already  =findDiagram(name);
        if (already != null) {
            diagrams.remove(already);
        }
        diagrams.add(diagram);
    }



    @SuppressWarnings({"unused"})
    public void update(@NonNull Diagram diagram) {
        String name = diagram.getName();
        if (name != null) {
            Diagram current = findDiagram(name);
            if (current != null) {
                diagrams.remove(current);
            }
            diagrams.add(diagram);
        } else {
            throw new NullPointerException("The map has no name");
        }
    }

    public String toJSONString() throws IOException {
        GuideMapper mapper = new GuideMapper();
        return mapper.writeValueAsString(this);
    }


    @Override
    public void filter(ITextFilter filter) {
        for (ITextFilterable diagram : diagrams) {
            diagram.filter(filter);
        }
    }
}
