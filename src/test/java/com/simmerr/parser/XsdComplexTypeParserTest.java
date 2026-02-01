package com.simmerr.parser;

import com.simmerr.xsdjson.model.ComplexTypeDefinition;
import com.simmerr.xsdjson.model.ContentModel;
import com.simmerr.xsdjson.model.ElementInfo;
import com.simmerr.xsdjson.model.ParsedSchema;
import com.simmerr.xsdjson.parser.SchemaParsingFacade;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class XsdComplexTypeParserTest {

    @Test
    void parseSequenceComplexType() {
        SchemaParsingFacade facade = new SchemaParsingFacade();
        ParsedSchema parsedSchema = facade.parseHostMessageSchema(
                TestHelper.getResourcePath("host-message-sequence.xsd")
        );

        ComplexTypeDefinition typeDefinition = (ComplexTypeDefinition) parsedSchema
                .getTypeRegistry()
                .get("HostMessageTypeTest", "http://example.com/host");

        assertEquals(ContentModel.SEQUENCE, typeDefinition.getContentModel());

        List<ElementInfo> children = typeDefinition.getChildElements();
        assertEquals(2, children.size());

        ElementInfo idElement = TestHelper.findElementByName(children, "id");
        assertNotNull(idElement);
        assertEquals(1, idElement.getMinOccurs());
        assertEquals(1, idElement.getMaxOccurs());

        ElementInfo countElement = TestHelper.findElementByName(children, "count");
        assertNotNull(countElement);
        assertEquals(0, countElement.getMinOccurs());
        assertEquals(3, countElement.getMaxOccurs());
    }

    @Test
    void parseMultipleRootElementsSequenceTypes() {
        SchemaParsingFacade facade = new SchemaParsingFacade();
        ParsedSchema parsedSchema = facade.parseHostMessageSchema(
                TestHelper.getResourcePath("multiple-elements.xsd")
        );

        assertEquals(3, parsedSchema.getRootElements().size());

        ComplexTypeDefinition personType = (ComplexTypeDefinition) parsedSchema
                .getTypeRegistry()
                .get("PersonType", "http://test.com/multiple");
        assertEquals(ContentModel.SEQUENCE, personType.getContentModel());
        assertEquals(1, personType.getChildElements().size());
        assertNotNull(TestHelper.findElementByName(personType.getChildElements(), "name"));

        ComplexTypeDefinition companyType = (ComplexTypeDefinition) parsedSchema
                .getTypeRegistry()
                .get("CompanyType", "http://test.com/multiple");
        assertEquals(ContentModel.SEQUENCE, companyType.getContentModel());
        assertEquals(1, companyType.getChildElements().size());
        assertNotNull(TestHelper.findElementByName(companyType.getChildElements(), "companyName"));
    }
}
