package com.simmerr.parser;

import com.simmerr.xsdjson.model.ElementInfo;
import com.simmerr.xsdjson.parser.XsdElementExtractor;
import com.simmerr.xsdjson.parser.XsdLoader;
import com.simmerr.xsdjson.parser.exceptions.XsdExtractorException;
import org.apache.xerces.xs.XSModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.simmerr.parser.TestHelper.findElementByName;
import static com.simmerr.parser.TestHelper.getResourcePath;
import static org.junit.jupiter.api.Assertions.*;

public class XsdElementExtractorTest {

    private XsdElementExtractor extractor;

    @BeforeEach
    void setUp() {
        extractor = new XsdElementExtractor();
    }

    @Test
    void testExtractSingleElement() {
        XsdLoader loader = new XsdLoader();
        XSModel model = loader.loadSchema(getResourcePath("simple-person.xsd"));
        assertNotNull(model, "XSModel should not be null");

        List<ElementInfo> elements = extractor.extractRootElements(model);

        assertEquals(1, elements.size());
        ElementInfo element = elements.get(0);
        assertEquals("person", element.getName());
        assertEquals("http://test.com/person", element.getNamespace());
        assertEquals("PersonType", element.getTypeName());
        assertTrue(element.getComplexType());
    }

    @Test
    void testExtractMultipleElements() {
        List<String> validationList = new ArrayList<>(Arrays.asList("person", "company", "product"));
        XsdLoader loader = new XsdLoader();
        XSModel model = loader.loadSchema(getResourcePath("multiple-elements.xsd"));
        assertNotNull(model, "XSModel should not be null");

        List<ElementInfo> elements = extractor.extractRootElements(model);

        assertEquals(3, elements.size());
        for (ElementInfo element : elements) {
            assertTrue(validationList.contains(element.getName()));
        }
    }

    @Test
    void testEmptySchema() {
        XsdLoader loader = new XsdLoader();
        XSModel model = loader.loadSchema(getResourcePath("empty-schema.xsd"));
        assertNotNull(model, "XSModel should not be null");

        List<ElementInfo> elements = extractor.extractRootElements(model);
        assertEquals(0, elements.size());
    }

    @Test
    void testFindElementByName() {
        XsdLoader loader = new XsdLoader();
        XSModel model = loader.loadSchema(getResourcePath("simple-person.xsd"));
        assertNotNull(model, "XSModel should not be null");
        String searchElement = "person";
        ElementInfo element = extractor.findRootElement(model, searchElement);

        assertEquals(searchElement, element.getName());
        assertEquals("person", element.getName());
        assertEquals("http://test.com/person", element.getNamespace());
        assertEquals("PersonType", element.getTypeName());
        assertTrue(element.getComplexType());
    }

    @Test
    void testFindElementNotFound() {
        XsdLoader loader = new XsdLoader();
        XSModel model = loader.loadSchema(getResourcePath("simple-person.xsd"));
        assertNotNull(model, "XSModel should not be null");
        String searchElement = "not-there";
        ElementInfo element = extractor.findRootElement(model, searchElement);

        assertNull(element);
    }

    @Test
    void testAnonymousType() {
        String addon = "_AnonymousType";
        String name = "address";
        XsdLoader loader = new XsdLoader();
        XSModel model = loader.loadSchema(getResourcePath("anonymous-type.xsd"));
        assertNotNull(model, "XSModel should not be null");
        List<ElementInfo> elements = extractor.extractRootElements(model);

        ElementInfo element = findElementByName(elements, name);
        assertNotNull(element);
        assertEquals(name, element.getName());
        assertEquals("http://test.com/anonymous", element.getNamespace());

        assertTrue(element.getComplexType());
        assertEquals(name + addon, element.getTypeName());
    }

    @Test
    void testSimpleTypeElement() {
        String name = "greeting";
        XsdLoader loader = new XsdLoader();
        XSModel model = loader.loadSchema(getResourcePath("anonymous-type.xsd"));
        assertNotNull(model, "XSModel should not be null");
        List<ElementInfo> elements = extractor.extractRootElements(model);

        ElementInfo element = findElementByName(elements, name);
        assertNotNull(element);
        assertEquals(name, element.getName());
        assertEquals("http://test.com/anonymous", element.getNamespace());

        assertFalse(element.getComplexType());
        assertEquals("string", element.getTypeName());
    }

    @Test
    void testNullModelThrowsException() {
        XsdExtractorException exception = assertThrows(
                XsdExtractorException.class,
                () -> extractor.extractRootElements(null)
        );

        assertNotNull(exception.getMessage(), "Exception should have a message");
        assertFalse(exception.getMessage().isBlank(), "Exception message should not be blank");
    }


    @Test
    void testNullElementNameThrowsException() {
        XsdLoader loader = new XsdLoader();
        XSModel model = loader.loadSchema(getResourcePath("anonymous-type.xsd"));
        assertNotNull(model, "XSModel should not be null");
        List<ElementInfo> elements = extractor.extractRootElements(model);

        XsdExtractorException exception = assertThrows(
                XsdExtractorException.class,
                () -> extractor.findRootElement(model, null)
        );

        assertNotNull(exception.getMessage(), "Exception should have a message");
        assertFalse(exception.getMessage().isBlank(), "Exception message should not be blank");
    }

    @Test
    void testFindElementWithNamespace() {
        String name = "address";
        XsdLoader loader = new XsdLoader();
        XSModel model = loader.loadSchema(getResourcePath("anonymous-type.xsd"));
        assertNotNull(model, "XSModel should not be null");
        List<ElementInfo> elements = extractor.extractRootElements(model);

        ElementInfo element = extractor.findRootElement(model, name, "http://test.com/anonymous");
        assertNotNull(element);
        assertEquals(name, element.getName());
        assertEquals("http://test.com/anonymous", element.getNamespace());

        assertTrue(element.getComplexType());
        assertEquals("address_AnonymousType", element.getTypeName());
    }

    @Test
    void testSchemaWithImport() {
        String name = "person";
        XsdLoader loader = new XsdLoader();
        XSModel model = loader.loadSchema(getResourcePath("person-with-import.xsd"));
        assertNotNull(model, "XSModel should not be null");
        List<ElementInfo> elements = extractor.extractRootElements(model);

        assertEquals(1, elements.size());
        ElementInfo element = elements.get(0);
        assertNotNull(element);
        assertEquals(name, element.getName());
        assertEquals("http://test.com/person", element.getNamespace());

        assertTrue(element.getComplexType());
        assertEquals("PersonType", element.getTypeName());
    }
}
