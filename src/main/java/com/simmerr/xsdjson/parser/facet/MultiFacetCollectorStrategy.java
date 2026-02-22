package com.simmerr.xsdjson.parser.facet;

import com.simmerr.xsdjson.model.FacetType;
import com.simmerr.xsdjson.parser.XsdParsingHelper;
import org.apache.xerces.xs.StringList;
import org.apache.xerces.xs.XSMultiValueFacet;
import org.apache.xerces.xs.XSObject;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSSimpleTypeDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MultiFacetCollectorStrategy implements FacetCollectorStrategy {
    private static final Logger logger = LoggerFactory.getLogger(MultiFacetCollectorStrategy.class);

    @Override
    public void collect(XSSimpleTypeDefinition type, Map<FacetType, List<String>> out, XsdParsingHelper helper) {
        XSObjectList multiFacets = type.getMultiValueFacets();
        for (int i = 0; i < multiFacets.getLength(); i++) {
            XSObject facet = (XSObject) multiFacets.item(i);
            if (!(facet instanceof XSMultiValueFacet)) {
                continue;
            }
            XSMultiValueFacet multiFacet = (XSMultiValueFacet) facet;
            FacetType facetType = helper.mapFacetKind(multiFacet.getFacetKind());
            if (facetType == null) {
                continue;
            }
            StringList values = multiFacet.getLexicalFacetValues();
            for (int j = 0; j < values.getLength(); j++) {
                out.computeIfAbsent(facetType, k -> new ArrayList<>()).add(values.item(j));
            }
            logger.debug("Collected multi facet {} with {} value(s) for type {}", facetType, values.getLength(), type.getName());
        }
    }
}
