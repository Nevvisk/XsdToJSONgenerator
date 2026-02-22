package com.simmerr.xsdjson.model;

import java.util.List;
import java.util.Map;

public class SimpleTypeDefinition implements TypeDefinition {

    protected String name;
    protected String namespace;
    protected String baseType;
    protected Map<FacetType, List<String>> facets;

    public SimpleTypeDefinition(String name, String namespace, String baseType, Map<FacetType, List<String>> facets) {
        this.name = name;
        this.namespace = namespace;
        this.baseType = baseType;
        this.facets = facets;
    }

    public SimpleTypeDefinition() {};

    public String getBaseType() {
        return baseType;
    }

    public void setBaseType(String baseType) {
        this.baseType = baseType;
    }

    public Map<FacetType, List<String>> getFacets() {
        return facets;
    }

    public void setFacets(Map<FacetType, List<String>> facets) {
        this.facets = facets;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNamespace(String nameSpace) {
        this.namespace = nameSpace;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getNamespace() {
        return namespace;
    }
}
