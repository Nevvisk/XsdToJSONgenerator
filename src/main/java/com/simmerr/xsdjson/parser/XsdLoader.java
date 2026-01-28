package com.simmerr.xsdjson.parser;

import com.simmerr.xsdjson.parser.exceptions.XsdLoadException;
import org.apache.xerces.impl.xs.XMLSchemaLoader;
import org.apache.xerces.xs.XSModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class XsdLoader {

    private static final Logger logger = LoggerFactory.getLogger(XsdLoader.class);

    public XSModel loadSchema(String filePath) {
        File file = createFileFromPath(filePath);

        try {
            XMLSchemaLoader schemaLoader = new XMLSchemaLoader();

            XSModel model = schemaLoader.loadURI(file.toURI().toString());

            if (model == null) {
                logger.info("Failed to parse the XSD schema: {}", file.getAbsolutePath());
                throw new XsdLoadException("Failed to parse XSD schema (invalid format)");
            }
            logger.info("Successfully loaded XSD Schema");
            return model;
        } catch (Exception e) {
            logger.error("Error loading XSD schema: {}", e.getMessage(), e);
            throw new XsdLoadException("Error loading XSD Schema: " + e.getMessage(), e);
        }
    }

    private File createFileFromPath(String xsdPath) {
        logger.info("Loading File from path {}", xsdPath);
        File file = new File(xsdPath);
        if (!file.exists()) {
            logger.error("XSD file not found: {}", file.getAbsolutePath());
            throw new XsdLoadException("XSD file does not exist: " + file.getAbsolutePath());
        }

        if (!file.isFile()) {
            logger.error("Path is not a file: {}", file.getAbsolutePath());
            throw new XsdLoadException("Path is not a file: " + file.getAbsolutePath());
        }
        return file;
    }
}
