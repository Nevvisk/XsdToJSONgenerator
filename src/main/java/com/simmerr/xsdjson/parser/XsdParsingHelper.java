package com.simmerr.xsdjson.parser;

import com.simmerr.xsdjson.model.ElementInfo;
import com.simmerr.xsdjson.model.FacetType;
import org.apache.xerces.xs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XsdParsingHelper {
    private static final Logger logger = LoggerFactory.getLogger(XsdParsingHelper.class);

    public ElementInfo getElementInfo(XSElementDeclaration obj) {
        String name = obj.getName();
        String namespace = obj.getNamespace();
        XSTypeDefinition typeDefinition = obj.getTypeDefinition();

        String typeName = typeDefinition.getName();
        String typeNamespace = typeDefinition.getNamespace();
        boolean isComplexType = (typeDefinition.getTypeCategory() == XSTypeDefinition.COMPLEX_TYPE);

        if (null == typeName || typeName.isEmpty()) {
            typeName = name + "_AnonymousType";
            logger.debug("Element {} has anonymous type", name);
        }
        return new ElementInfo(
                name, namespace, typeName, isComplexType, typeNamespace, 1, 1
        );
    }

    public Map<FacetType, List<String>> buildFacetMap(XSObjectList facets) {
        Map<FacetType, List<String>> map = new HashMap<>();
        for (int i = 0; i < facets.getLength(); i++) {
            XSObject facet = (XSObject) facets.get(i);
            if (facet instanceof XSFacet) {
                XSFacet singleFacet = (XSFacet) facet;
                FacetType facetType = mapFacetToType(singleFacet.getFacetKind());
                if (facetType != null) {
                    logger.debug("Mapped facet {} -> {}", singleFacet.getFacetKind(), facetType);
                    map.computeIfAbsent(facetType, k -> new ArrayList<>()).add(singleFacet.getLexicalFacetValue());
                }
            } else {
                XSMultiValueFacet multiFacet = (XSMultiValueFacet) facet;
                FacetType facetType = mapFacetToType(multiFacet.getFacetKind());
                if (facetType == null) {
                    continue;
                }

                StringList values = multiFacet.getLexicalFacetValues();
                logger.debug("Mapped multi-value facet {} -> {} ({} value(s))", multiFacet.getFacetKind(), facetType, values.getLength());
                for (int j = 0; j < values.getLength(); j++) {
                    map.computeIfAbsent(facetType, k -> new ArrayList<>())
                            .add(values.item(j));
                }
            }
        }
        return map;
    }

    private FacetType mapFacetToType(short facetKind) {
        switch (facetKind) {
            case XSSimpleTypeDefinition.FACET_LENGTH -> {
                return FacetType.LENGTH;
            }
            case XSSimpleTypeDefinition.FACET_MINLENGTH -> {
                return FacetType.MIN_LENGTH;
            }
            case XSSimpleTypeDefinition.FACET_MAXLENGTH -> {
                return FacetType.MAX_LENGTH;
            }
            case XSSimpleTypeDefinition.FACET_PATTERN -> {
                return FacetType.PATTERN;
            }
            case XSSimpleTypeDefinition.FACET_ENUMERATION -> {
                return FacetType.ENUMERATION;
            }
            case XSSimpleTypeDefinition.FACET_MININCLUSIVE -> {
                return FacetType.MIN_INCLUSIVE;
            }
            case XSSimpleTypeDefinition.FACET_MAXINCLUSIVE -> {
                return FacetType.MAX_INCLUSIVE;
            }
            case XSSimpleTypeDefinition.FACET_MINEXCLUSIVE -> {
                return FacetType.MIN_EXCLUSIVE;
            }
            case XSSimpleTypeDefinition.FACET_MAXEXCLUSIVE -> {
                return FacetType.MAX_EXCLUSIVE;
            }
            case XSSimpleTypeDefinition.FACET_TOTALDIGITS -> {
                return FacetType.TOTAL_DIGITS;
            }
            case XSSimpleTypeDefinition.FACET_FRACTIONDIGITS -> {
                return FacetType.FRACTION_DIGITS;
            }
            case XSSimpleTypeDefinition.FACET_WHITESPACE -> {
                return FacetType.WHITE_SPACE;
            }
            default -> {
                logger.debug("Unknown/unsupported facet kind: {}", facetKind);
                return null;
            }
        }
    }
}
