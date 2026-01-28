package com.simmerr.xsdjson.model;

import java.util.Collections;
import java.util.List;

public class ComplexTypeDefinition {

    private final String name;
    private final String namespace;
    private final List<ElementInfo> sequenceElements;
    // TODO: Later add choiceElements, allElements, attributes

    public ComplexTypeDefinition(String name, String namespace,
                                 List<ElementInfo> sequenceElements) {
        this.name = name;
        this.namespace = namespace;
        this.sequenceElements = sequenceElements != null ?
                Collections.unmodifiableList(sequenceElements) :
                Collections.emptyList();
    }

    public String getName() { return name; }
    public String getNamespace() { return namespace; }
    public List<ElementInfo> getSequenceElements() { return sequenceElements; }
    public boolean hasSequence() { return !sequenceElements.isEmpty(); }

    @Override
    public String toString() {
        return "ComplexTypeDefinition{" +
                "name='" + name + '\'' +
                ", namespace='" + namespace + '\'' +
                ", sequenceElements=" + sequenceElements.size() +
                '}';
    }
}