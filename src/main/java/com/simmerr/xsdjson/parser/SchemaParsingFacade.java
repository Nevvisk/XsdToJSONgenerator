package com.simmerr.xsdjson.parser;

import com.simmerr.xsdjson.model.ParsedSchema;

public class SchemaParsingFacade {

    private final XsdSchemaParser schemaParser;

    public SchemaParsingFacade() {
        this.schemaParser = new XsdSchemaParser();
    }

    public SchemaParsingFacade(XsdSchemaParser schemaParser) {
        this.schemaParser = schemaParser;
    }

    public ParsedSchema parseHostMessageSchema(String xsdPath) {
        return schemaParser.parseHostMessageSchema(xsdPath);
    }
}
