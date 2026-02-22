package com.simmerr.xsdjson.parser;

import com.simmerr.xsdjson.model.ElementInfo;
import com.simmerr.xsdjson.model.ParsedSchema;
import com.simmerr.xsdjson.model.TypeRegistry;
import org.apache.xerces.xs.XSModel;
import org.apache.xerces.xs.XSTypeDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class XsdSchemaParser {
    private static final Logger logger = LoggerFactory.getLogger(XsdSchemaParser.class);

    private final XsdLoader loader;
    private final XsdElementExtractor elementExtractor;
    private final List<TypeParser> typeParsers;

    public XsdSchemaParser() {
        this(new XsdLoader(), new XsdElementExtractor(), new XsdComplexTypeParser(), new XsdSimpleTypeParser());
    }

    public XsdSchemaParser(XsdLoader loader, XsdElementExtractor elementExtractor, XsdComplexTypeParser complexTypeParser, XsdSimpleTypeParser simpleTypeParser) {
        this(loader, elementExtractor, List.of(
                new SimpleTypeParserAdapter(simpleTypeParser),
                new ComplexTypeParserAdapter(complexTypeParser)
        ));
    }

    public XsdSchemaParser(XsdLoader loader, XsdElementExtractor elementExtractor, List<TypeParser> typeParsers) {
        this.loader = loader;
        this.elementExtractor = elementExtractor;
        this.typeParsers = new ArrayList<>(typeParsers);
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
            boolean parsed = false;
            for (TypeParser parser : typeParsers) {
                if (parser.supports(typeDefinition)) {
                    logger.debug("Dispatching type {} for root element {} to {}", element.getTypeName(), element.getName(), parser.getClass().getSimpleName());
                    parser.parse(typeDefinition, registry);
                    parsed = true;
                    break;
                }
            }
            if (!parsed) {
                logger.warn("No parser found for type {} (namespace={})", element.getTypeName(), element.getTypeNamespace());
            }
        }

        return new ParsedSchema(rootElements, registry);
    }
}
