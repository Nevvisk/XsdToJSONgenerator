package com.simmerr.xsdjson.model;

import java.util.List;

public class ComplexTypeDefinition implements TypeDefinition{

    protected String name;
    protected String namespace;
    protected ContentModel contentModel;
    protected List<ElementInfo> childElements;
    protected boolean isMixed;
    protected boolean isAbstract;

    public ComplexTypeDefinition(String name, String namespace, ContentModel contentModel, List<ElementInfo> childElements, boolean isMixed, boolean isAbstract) {
        this.name = name;
        this.namespace = namespace;
        this.contentModel = contentModel;
        this.childElements = childElements;
        this.isMixed = isMixed;
        this.isAbstract = isAbstract;
    }

    public ComplexTypeDefinition() {};

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public ContentModel getContentModel() {
        return contentModel;
    }

    public void setContentModel(ContentModel contentModel) {
        this.contentModel = contentModel;
    }

    public List<ElementInfo> getChildElements() {
        return childElements;
    }

    public void setChildElements(List<ElementInfo> childElements) {
        this.childElements = childElements;
    }

    public boolean isMixed() {
        return isMixed;
    }

    public void setMixed(boolean mixed) {
        isMixed = mixed;
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public void setAbstract(boolean anAbstract) {
        isAbstract = anAbstract;
    }
}