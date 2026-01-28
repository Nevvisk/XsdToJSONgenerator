package com.simmerr.parser;

import com.simmerr.xsdjson.parser.exceptions.XsdLoadException;
import com.simmerr.xsdjson.parser.XsdLoader;
import org.apache.xerces.xs.XSElementDeclaration;
import org.apache.xerces.xs.XSModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class XsdLoaderTest {

    private String TEST_RESOURCES_PATH = "src/test/resources/";
    private String FILE_NAME = "simple-person.xsd";

    @Test
    void testLoadValidSchema() {
        XsdLoader loader = new XsdLoader();
        String xsdPath = TEST_RESOURCES_PATH + FILE_NAME;

        XSModel model = loader.loadSchema(xsdPath);

        assertNotNull(model, "XSModel should not be null");

        XSElementDeclaration elementDeclaration = model.getElementDeclaration("person", "http://test.com/person");
        assertNotNull(elementDeclaration, "ElementDeclaration should not be null");
        assertEquals("person", elementDeclaration.getName());
    }

    @Test
    void testLoadNonExistentFile() {
        XsdLoader loader = new XsdLoader();
        String xsdPath = TEST_RESOURCES_PATH + "invald.xsd";

        XsdLoadException exception = assertThrows(
                XsdLoadException.class,
                () -> loader.loadSchema(xsdPath)
        );

        assertTrue(exception.getMessage().contains("does not exist"));
    }

    @Test
    void testLoadInvalidXsd(@TempDir Path dir) throws IOException {
        XsdLoader loader = new XsdLoader();

        Path invalidPath = dir.resolve("invalid.txt");
        Files.writeString(invalidPath, "This is invalid XML.");

        XsdLoadException exception = assertThrows(
                XsdLoadException.class,
                () -> loader.loadSchema(invalidPath.toString())
        );

        assertTrue(exception.getMessage().contains("Error loading XSD Schema"));
    }
}
