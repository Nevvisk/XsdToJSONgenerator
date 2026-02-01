package com.simmerr.xsdjson.model;

import java.util.Map;

public class SingleTypeDefinition implements TypeDefinition {

    protected String name;
    protected String namespace;
    protected String baseType;
    protected Map<FacetType, String> facets;

    public SingleTypeDefinition(String name, String namespace, String baseType, Map<FacetType, String> facets) {
        this.name = name;
        this.namespace = namespace;
        this.baseType = baseType;
        this.facets = facets;
    }

    public String getBaseType() {
        return baseType;
    }

    public void setBaseType(String baseType) {
        this.baseType = baseType;
    }

    public Map<FacetType, String> getFacets() {
        return facets;
    }

    public void setFacets(Map<FacetType, String> facets) {
        this.facets = facets;
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
