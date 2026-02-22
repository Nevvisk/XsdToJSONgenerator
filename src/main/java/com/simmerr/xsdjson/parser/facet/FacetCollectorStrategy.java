package com.simmerr.xsdjson.parser.facet;

import com.simmerr.xsdjson.model.FacetType;
import com.simmerr.xsdjson.parser.XsdParsingHelper;
import org.apache.xerces.xs.XSSimpleTypeDefinition;

import java.util.List;
import java.util.Map;

public interface FacetCollectorStrategy {
    void collect(XSSimpleTypeDefinition type, Map<FacetType, List<String>> out, XsdParsingHelper helper);
}
