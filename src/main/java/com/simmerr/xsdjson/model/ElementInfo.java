package com.simmerr.xsdjson.model;

public class ElementInfo {

    private String name;
    private String namespace;
    private String typeName;
    private boolean isComplexType;
    private String typeNamespace;
    private int minOccurs;
    private int maxOccurs;

    public ElementInfo(String name, String namespace, String typeName, Boolean isComplexType, String typeNamespace, int minOccurs, int maxOccurs) {
        this.name = name;
        this.namespace = namespace;
        this.typeName = typeName;
        this.isComplexType = isComplexType;
        this.typeNamespace = typeNamespace;
        this.minOccurs = minOccurs;
        this.maxOccurs = maxOccurs;
    }

    public ElementInfo() {};

    public String getTypeNamespace() {
        return typeNamespace;
    }

    public boolean getComplexType() {
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
        return namespace == null || namespace.isEmpty() ? name : "{" + namespace + "}" + name;
    }

    public int getMinOccurs() {
        return minOccurs;
    }

    public int getMaxOccurs() {
        return maxOccurs;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setComplexType(boolean complexType) {
        isComplexType = complexType;
    }

    public void setTypeNamespace(String typeNamespace) {
        this.typeNamespace = typeNamespace;
    }

    public void setMinOccurs(int minOccurs) {
        this.minOccurs = minOccurs;
    }

    public void setMaxOccurs(int maxOccurs) {
        this.maxOccurs = maxOccurs;
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
