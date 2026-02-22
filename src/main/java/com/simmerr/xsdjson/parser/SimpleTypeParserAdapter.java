package com.simmerr.xsdjson.parser;

import com.simmerr.xsdjson.model.TypeRegistry;
import org.apache.xerces.xs.XSSimpleTypeDefinition;
import org.apache.xerces.xs.XSTypeDefinition;

public class SimpleTypeParserAdapter implements TypeParser {

    private final XsdSimpleTypeParser delegate;

    public SimpleTypeParserAdapter(XsdSimpleTypeParser delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean supports(XSTypeDefinition typeDefinition) {
        return typeDefinition instanceof XSSimpleTypeDefinition;
    }

    @Override
    public void parse(XSTypeDefinition typeDefinition, TypeRegistry registry) {
        delegate.parseSimpleType((XSSimpleTypeDefinition) typeDefinition, registry);
    }
}
