package com.simmerr.xsdjson.parser.strategy;

import com.simmerr.xsdjson.model.ParsedGroup;
import com.simmerr.xsdjson.model.TypeRegistry;
import org.apache.xerces.xs.XSModelGroup;

public interface ModelGroupStrategy {
    short compositor();

    ParsedGroup parse(XSModelGroup group, TypeRegistry registry, ModelGroupContext context);
}
