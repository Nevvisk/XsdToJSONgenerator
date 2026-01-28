package com.simmerr.xsdjson.model;

public class ElementInfo {

    private final String name;
    private final String namespace;
    private final String typeName;
    private final Boolean isComplexType;
    private final String typeNamespace;
    private final int minOccurs;
    private final int maxOccurs;

    public ElementInfo(String name, String namespace, String typeName, Boolean isComplexType, String typeNamespace, int minOccurs, int maxOccurs) {
        this.name = name;
        this.namespace = namespace;
        this.typeName = typeName;
        this.isComplexType = isComplexType;
        this.typeNamespace = typeNamespace;
        this.minOccurs = minOccurs;
        this.maxOccurs = maxOccurs;
    }

    public String getTypeNamespace() {
        return typeNamespace;
    }

    public Boolean getComplexType() {
        return isComplexType;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getName() {
        return name;
    }

    public String getQualifiedName() {
        return namespace == null ? name : "{" + namespace + "}";
    }

    @Override
    public String toString() {
        return "ElementInfo{" +
                "name='" + name + '\'' +
                ", namespace='" + namespace + '\'' +
                ", typeName='" + typeName + '\'' +
                ", isComplexType=" + isComplexType +
                ", typeNamespace='" + typeNamespace + '\'' +
                '}';
    }
}
