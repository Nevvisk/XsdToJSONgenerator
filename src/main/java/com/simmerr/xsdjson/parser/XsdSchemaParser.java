package com.simmerr.xsdjson.parser;

import com.simmerr.xsdjson.model.ElementInfo;
import com.simmerr.xsdjson.model.ParsedSchema;
import com.simmerr.xsdjson.model.TypeRegistry;
import org.apache.xerces.xs.XSComplexTypeDefinition;
import org.apache.xerces.xs.XSModel;
import org.apache.xerces.xs.XSSimpleTypeDefinition;
import org.apache.xerces.xs.XSTypeDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class XsdSchemaParser {
    private static final Logger logger = LoggerFactory.getLogger(XsdSchemaParser.class);

    private final XsdLoader loader;
    private final XsdElementExtractor elementExtractor;
    private final XsdComplexTypeParser complexTypeParser;
    private final XsdSimpleTypeParser simpleTypeParser;

    public XsdSchemaParser() {
        this(new XsdLoader(), new XsdElementExtractor(), new XsdComplexTypeParser(), new XsdSimpleTypeParser());
    }

    public XsdSchemaParser(XsdLoader loader, XsdElementExtractor elementExtractor, XsdComplexTypeParser complexTypeParser, XsdSimpleTypeParser simpleTypeParser) {
        this.loader = loader;
        this.elementExtractor = elementExtractor;
        this.complexTypeParser = complexTypeParser;
        this.simpleTypeParser = simpleTypeParser;
    }

    public ParsedSchema parseHostMessageSchema(String xsdPath) {
        logger.info("Parsing schema from {}", xsdPath);
        XSModel model = loader.loadSchema(xsdPath);
        List<ElementInfo> rootElements = elementExtractor.extractRootElements(model);
        logger.info("Found {} root element(s)", rootElements.size());

        TypeRegistry registry = TypeRegistry.getInstance();
        registry.clear();
        logger.debug("Type registry cleared before parsing");
        for (ElementInfo element : rootElements) {
            XSTypeDefinition typeDefinition = model.getTypeDefinition(element.getTypeName(), element.getTypeNamespace());
            if (typeDefinition instanceof XSSimpleTypeDefinition) {
                logger.debug("Parsing simple type {} for root element {}", element.getTypeName(), element.getName());
                simpleTypeParser.parseSimpleType((XSSimpleTypeDefinition) typeDefinition, registry);
            }
            if (typeDefinition instanceof XSComplexTypeDefinition) {
                logger.debug("Parsing complex type {} for root element {}", element.getTypeName(), element.getName());
                complexTypeParser.parseComplexType((XSComplexTypeDefinition) typeDefinition, registry);
            }
        }

        return new ParsedSchema(rootElements, registry);
    }
}
