package com.simmerr.parser;

import com.simmerr.xsdjson.model.FacetType;
import com.simmerr.xsdjson.model.ParsedSchema;
import com.simmerr.xsdjson.model.SimpleTypeDefinition;
import com.simmerr.xsdjson.model.TypeDefinition;
import com.simmerr.xsdjson.parser.SchemaParsingFacade;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class XsdSimpleTypeParserTest {

    @Test
    void parseSimpleTypeStringAndNumericRestrictions() {
        SchemaParsingFacade facade = new SchemaParsingFacade();
        ParsedSchema parsedSchema = facade.parseHostMessageSchema(
                TestHelper.getResourcePath("simple-type-restrictions.xsd")
        );

        TypeDefinition codeTypeDefinition = parsedSchema.getTypeRegistry()
                .get("CodeType", "http://test.com/simpletypes");
        assertInstanceOf(SimpleTypeDefinition.class, codeTypeDefinition);

        SimpleTypeDefinition codeType = (SimpleTypeDefinition) codeTypeDefinition;
        Map<FacetType, List<String>> codeFacets = codeType.getFacets();
        assertEquals(List.of("3"), codeFacets.get(FacetType.MIN_LENGTH));
        assertEquals(List.of("5"), codeFacets.get(FacetType.MAX_LENGTH));
        assertEquals(List.of("[A-Z0-9]+"), codeFacets.get(FacetType.PATTERN));

        TypeDefinition quantityTypeDefinition = parsedSchema.getTypeRegistry()
                .get("QuantityType", "http://test.com/simpletypes");
        assertInstanceOf(SimpleTypeDefinition.class, quantityTypeDefinition);

        SimpleTypeDefinition quantityType = (SimpleTypeDefinition) quantityTypeDefinition;
        Map<FacetType, List<String>> quantityFacets = quantityType.getFacets();
        assertEquals(List.of("1"), quantityFacets.get(FacetType.MIN_INCLUSIVE));
        assertEquals(List.of("10"), quantityFacets.get(FacetType.MAX_INCLUSIVE));
    }

    @Test
    void parseSimpleTypeEnumerationRestrictions() {
        SchemaParsingFacade facade = new SchemaParsingFacade();
        ParsedSchema parsedSchema = facade.parseHostMessageSchema(
                TestHelper.getResourcePath("simple-type-enum-only.xsd")
        );

        TypeDefinition statusTypeDefinition = parsedSchema.getTypeRegistry()
                .get("StatusTypeEnumOnly", "http://test.com/simpletypes-enum");
        assertInstanceOf(SimpleTypeDefinition.class, statusTypeDefinition);

        SimpleTypeDefinition statusType = (SimpleTypeDefinition) statusTypeDefinition;
        Map<FacetType, List<String>> facets = statusType.getFacets();

        assertEquals(
                List.of("NEW", "PROCESSING", "DONE"),
                facets.get(FacetType.ENUMERATION)
        );
    }
}
