package com.simmerr.parser;

import com.simmerr.xsdjson.model.ComplexTypeDefinition;
import com.simmerr.xsdjson.model.ContentModel;
import com.simmerr.xsdjson.model.ElementInfo;
import com.simmerr.xsdjson.model.FacetType;
import com.simmerr.xsdjson.model.ParsedSchema;
import com.simmerr.xsdjson.model.SimpleTypeDefinition;
import com.simmerr.xsdjson.model.TypeDefinition;
import com.simmerr.xsdjson.parser.SchemaParsingFacade;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class XsdParserWalkthroughTest {

    @Test
    void parseWalkthroughSchema_shouldExposeSimpleAndComplexTypeBehavior() {
        SchemaParsingFacade facade = new SchemaParsingFacade();
        ParsedSchema parsedSchema = facade.parseHostMessageSchema(
                TestHelper.getResourcePath("full-parser-walkthrough.xsd")
        );

        assertEquals(6, parsedSchema.getRootElements().size());

        assertOrderType(parsedSchema);
        assertLineType(parsedSchema);
        assertPingType(parsedSchema);
        assertCodeType(parsedSchema);
        assertStatusType(parsedSchema);
        assertQuantityType(parsedSchema);
    }

    private void assertOrderType(ParsedSchema parsedSchema) {
        TypeDefinition typeDefinition = parsedSchema.getTypeRegistry()
                .get("OrderType", "http://example.com/full");
        assertInstanceOf(ComplexTypeDefinition.class, typeDefinition);

        ComplexTypeDefinition orderType = (ComplexTypeDefinition) typeDefinition;
        assertEquals(ContentModel.SEQUENCE, orderType.getContentModel());
        assertEquals(3, orderType.getChildElements().size());

        ElementInfo code = TestHelper.findElementByName(orderType.getChildElements(), "code");
        assertNotNull(code);
        assertEquals(1, code.getMinOccurs());
        assertEquals(1, code.getMaxOccurs());

        ElementInfo status = TestHelper.findElementByName(orderType.getChildElements(), "status");
        assertNotNull(status);
        assertEquals(0, status.getMinOccurs());
        assertEquals(1, status.getMaxOccurs());

        ElementInfo line = TestHelper.findElementByName(orderType.getChildElements(), "line");
        assertNotNull(line);
        assertEquals(1, line.getMinOccurs());
        assertEquals(3, line.getMaxOccurs());
    }

    private void assertLineType(ParsedSchema parsedSchema) {
        TypeDefinition typeDefinition = parsedSchema.getTypeRegistry()
                .get("LineType", "http://example.com/full");
        assertInstanceOf(ComplexTypeDefinition.class, typeDefinition);

        ComplexTypeDefinition lineType = (ComplexTypeDefinition) typeDefinition;
        assertEquals(ContentModel.CHOICE, lineType.getContentModel());
        assertEquals(2, lineType.getChildElements().size());

        assertNotNull(TestHelper.findElementByName(lineType.getChildElements(), "qty"));
        assertNotNull(TestHelper.findElementByName(lineType.getChildElements(), "note"));
    }

    private void assertPingType(ParsedSchema parsedSchema) {
        TypeDefinition typeDefinition = parsedSchema.getTypeRegistry()
                .get("PingType", "http://example.com/full");
        assertInstanceOf(ComplexTypeDefinition.class, typeDefinition);

        ComplexTypeDefinition pingType = (ComplexTypeDefinition) typeDefinition;
        assertEquals(ContentModel.ALL, pingType.getContentModel());
        assertEquals(2, pingType.getChildElements().size());

        ElementInfo id = TestHelper.findElementByName(pingType.getChildElements(), "id");
        assertNotNull(id);
        assertEquals(1, id.getMinOccurs());
        assertEquals(1, id.getMaxOccurs());

        ElementInfo timestamp = TestHelper.findElementByName(pingType.getChildElements(), "timestamp");
        assertNotNull(timestamp);
        assertEquals(0, timestamp.getMinOccurs());
        assertEquals(1, timestamp.getMaxOccurs());
    }

    private void assertCodeType(ParsedSchema parsedSchema) {
        TypeDefinition typeDefinition = parsedSchema.getTypeRegistry()
                .get("CodeType", "http://example.com/full");
        assertInstanceOf(SimpleTypeDefinition.class, typeDefinition);

        SimpleTypeDefinition codeType = (SimpleTypeDefinition) typeDefinition;
        assertEquals("string", codeType.getBaseType());

        Map<FacetType, List<String>> facets = codeType.getFacets();
        assertEquals(List.of("3"), facets.get(FacetType.MIN_LENGTH));
        assertEquals(List.of("8"), facets.get(FacetType.MAX_LENGTH));
        assertTrue(facets.get(FacetType.PATTERN).contains("[A-Z0-9]+"));
    }

    private void assertStatusType(ParsedSchema parsedSchema) {
        TypeDefinition typeDefinition = parsedSchema.getTypeRegistry()
                .get("StatusType", "http://example.com/full");
        assertInstanceOf(SimpleTypeDefinition.class, typeDefinition);

        SimpleTypeDefinition statusType = (SimpleTypeDefinition) typeDefinition;
        assertEquals("string", statusType.getBaseType());

        Map<FacetType, List<String>> facets = statusType.getFacets();
        assertEquals(List.of("NEW", "PROCESSING", "DONE"), facets.get(FacetType.ENUMERATION));
    }

    private void assertQuantityType(ParsedSchema parsedSchema) {
        TypeDefinition typeDefinition = parsedSchema.getTypeRegistry()
                .get("QuantityType", "http://example.com/full");
        assertInstanceOf(SimpleTypeDefinition.class, typeDefinition);

        SimpleTypeDefinition quantityType = (SimpleTypeDefinition) typeDefinition;
        assertEquals("int", quantityType.getBaseType());

        Map<FacetType, List<String>> facets = quantityType.getFacets();
        assertEquals(List.of("1"), facets.get(FacetType.MIN_INCLUSIVE));
        assertEquals(List.of("999"), facets.get(FacetType.MAX_INCLUSIVE));
    }
}
