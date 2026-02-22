package com.simmerr.xsdjson.parser.facet;

import com.simmerr.xsdjson.model.FacetType;
import com.simmerr.xsdjson.parser.XsdParsingHelper;
import org.apache.xerces.xs.XSFacet;
import org.apache.xerces.xs.XSObject;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSSimpleTypeDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SingleFacetCollectorStrategy implements FacetCollectorStrategy {
    private static final Logger logger = LoggerFactory.getLogger(SingleFacetCollectorStrategy.class);

    @Override
    public void collect(XSSimpleTypeDefinition type, Map<FacetType, List<String>> out, XsdParsingHelper helper) {
        XSObjectList singleFacets = type.getFacets();
        for (int i = 0; i < singleFacets.getLength(); i++) {
            XSObject facet = (XSObject) singleFacets.item(i);
            if (!(facet instanceof XSFacet)) {
                continue;
            }
            XSFacet singleFacet = (XSFacet) facet;
            FacetType facetType = helper.mapFacetKind(singleFacet.getFacetKind());
            if (facetType == null) {
                continue;
            }
            out.computeIfAbsent(facetType, k -> new ArrayList<>()).add(singleFacet.getLexicalFacetValue());
            logger.debug("Collected single facet {} for type {}", facetType, type.getName());
        }
    }
}
