package com.simmerr.xsdjson.parser;

import com.simmerr.xsdjson.model.TypeRegistry;
import org.apache.xerces.xs.XSComplexTypeDefinition;
import org.apache.xerces.xs.XSTypeDefinition;

public class ComplexTypeParserAdapter implements TypeParser {

    private final XsdComplexTypeParser delegate;

    public ComplexTypeParserAdapter(XsdComplexTypeParser delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean supports(XSTypeDefinition typeDefinition) {
        return typeDefinition instanceof XSComplexTypeDefinition;
    }

    @Override
    public void parse(XSTypeDefinition typeDefinition, TypeRegistry registry) {
        delegate.parseComplexType((XSComplexTypeDefinition) typeDefinition, registry);
    }
}
