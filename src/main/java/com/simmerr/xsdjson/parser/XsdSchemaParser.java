package com.simmerr.xsdjson.parser;

import com.simmerr.xsdjson.model.ElementInfo;
import com.simmerr.xsdjson.model.ParsedSchema;
import com.simmerr.xsdjson.model.TypeRegistry;
import org.apache.xerces.xs.XSComplexTypeDefinition;
import org.apache.xerces.xs.XSModel;
import org.apache.xerces.xs.XSTypeDefinition;

import java.util.List;

public class XsdSchemaParser {

    private final XsdLoader loader;
    private final XsdElementExtractor elementExtractor;
    private final XsdComplexTypeParser complexTypeParser;

    public XsdSchemaParser() {
        this(new XsdLoader(), new XsdElementExtractor(), new XsdComplexTypeParser());
    }

    public XsdSchemaParser(XsdLoader loader, XsdElementExtractor elementExtractor, XsdComplexTypeParser complexTypeParser) {
        this.loader = loader;
        this.elementExtractor = elementExtractor;
        this.complexTypeParser = complexTypeParser;
    }

    public ParsedSchema parseHostMessageSchema(String xsdPath) {
        XSModel model = loader.loadSchema(xsdPath);
        List<ElementInfo> rootElements = elementExtractor.extractRootElements(model);

        TypeRegistry registry = TypeRegistry.getInstance();
        for (ElementInfo element : rootElements) {
            if (!element.getComplexType()) {
                continue;
            }
            XSTypeDefinition typeDefinition = model.getTypeDefinition(element.getTypeName(), element.getTypeNamespace());
            if (typeDefinition instanceof XSComplexTypeDefinition) {
                complexTypeParser.parseComplexType((XSComplexTypeDefinition) typeDefinition, registry);
            }
        }

        return new ParsedSchema(rootElements, registry);
    }
}
