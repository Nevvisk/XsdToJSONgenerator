package com.simmerr.xsdjson.model;

import java.util.List;

public class ParsedSchema {

    private final List<ElementInfo> rootElements;
    private final TypeRegistry typeRegistry;

    public ParsedSchema(List<ElementInfo> rootElements, TypeRegistry typeRegistry) {
        this.rootElements = rootElements;
        this.typeRegistry = typeRegistry;
    }

    public List<ElementInfo> getRootElements() {
        return rootElements;
    }

    public TypeRegistry getTypeRegistry() {
        return typeRegistry;
    }
}
