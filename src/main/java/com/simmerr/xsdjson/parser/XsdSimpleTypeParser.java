package com.simmerr.xsdjson.parser;

import com.simmerr.xsdjson.model.FacetType;
import com.simmerr.xsdjson.model.SimpleTypeDefinition;
import com.simmerr.xsdjson.model.TypeRegistry;
import org.apache.xerces.xs.XSSimpleTypeDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XsdSimpleTypeParser {

    protected XsdParsingHelper helper = new XsdParsingHelper();
    private static final Logger logger = LoggerFactory.getLogger(XsdSimpleTypeParser.class);

    public void parseSimpleType(XSSimpleTypeDefinition type, TypeRegistry registry) {
        SimpleTypeDefinition definition = new SimpleTypeDefinition();
        String typeName = type.getName();
        if (typeName == null || typeName.isEmpty()) {
            typeName = "AnonymousType";
        }
        String typeNamespace = type.getNamespace();
        if (typeNamespace == null) {
            typeNamespace = "";
        }
        definition.setName(typeName);
        definition.setNamespace(typeNamespace);
        String baseTypeName = type.getBaseType() != null ? type.getBaseType().getName() : null;
        definition.setBaseType(baseTypeName);
        logger.debug("Parsing simple type {} (namespace={}, baseType={})", typeName, typeNamespace, baseTypeName);

        Map<FacetType, List<String>> facetMap = new HashMap<>();
        mergeFacetMap(facetMap, helper.buildFacetMap(type.getFacets()));
        mergeFacetMap(facetMap, helper.buildFacetMap(type.getMultiValueFacets()));
        definition.setFacets(facetMap);
        logger.debug("Parsed {} facet type(s) for simple type {}", facetMap.size(), typeName);
        registry.register(definition);
    }

    private void mergeFacetMap(Map<FacetType, List<String>> target, Map<FacetType, List<String>> source) {
        for (Map.Entry<FacetType, List<String>> entry : source.entrySet()) {
            target.computeIfAbsent(entry.getKey(), k -> new ArrayList<>()).addAll(entry.getValue());
        }
    }
}
