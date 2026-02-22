package com.simmerr.xsdjson.parser;

import com.simmerr.xsdjson.model.ElementInfo;
import com.simmerr.xsdjson.model.FacetType;
import org.apache.xerces.xs.XSElementDeclaration;
import org.apache.xerces.xs.XSSimpleTypeDefinition;
import org.apache.xerces.xs.XSTypeDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public FacetType mapFacetKind(short facetKind) {
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
