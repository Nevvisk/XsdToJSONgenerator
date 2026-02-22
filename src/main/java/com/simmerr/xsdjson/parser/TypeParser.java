package com.simmerr.xsdjson.parser;

import com.simmerr.xsdjson.model.TypeRegistry;
import org.apache.xerces.xs.XSTypeDefinition;

public interface TypeParser {

    boolean supports(XSTypeDefinition typeDefinition);

    void parse(XSTypeDefinition typeDefinition, TypeRegistry registry);
}
