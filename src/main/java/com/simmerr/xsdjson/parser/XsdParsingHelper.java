package com.simmerr.xsdjson.parser;

import com.simmerr.xsdjson.model.ElementInfo;
import org.apache.xerces.xs.XSElementDeclaration;
import org.apache.xerces.xs.XSTypeDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XsdParsingHelper {
    private static final Logger logger = LoggerFactory.getLogger(XsdParsingHelper.class);

    public ElementInfo getElementInfo(XSElementDeclaration obj) {
        String name = obj.getName();
        String namespace = obj.getNamespace();
        XSTypeDefinition typeDefinition = obj.getTypeDefinition();

        String typeName = typeDefinition.getName();
        String typeNamespace = typeDefinition.getNamespace();
        boolean isComplexType = (typeDefinition.getTypeCategory() == XSTypeDefinition.COMPLEX_TYPE);

        if (null == typeName || typeName.isEmpty()) {
            typeName = name + "_AnonymousType";
            logger.debug("Element {} has anonymous type", name);
        }
        return new ElementInfo(
                name, namespace, typeName, isComplexType, typeNamespace, 1, 1
        );
    }

}
