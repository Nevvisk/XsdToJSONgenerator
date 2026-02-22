package com.simmerr.xsdjson.parser.strategy;

import com.simmerr.xsdjson.model.ParsedGroup;
import com.simmerr.xsdjson.model.TypeRegistry;
import org.apache.xerces.xs.XSParticle;

@FunctionalInterface
public interface ModelGroupContext {
    ParsedGroup parseParticle(XSParticle particle, TypeRegistry registry);
}
