package com.simmerr.xsdjson.parser;

import com.simmerr.xsdjson.model.ElementInfo;
import com.simmerr.xsdjson.parser.exceptions.XsdExtractorException;
import org.apache.xerces.xs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.apache.xerces.xs.XSConstants.ELEMENT_DECLARATION;

public class XsdElementExtractor {

    private static final Logger logger = LoggerFactory.getLogger(XsdElementExtractor.class);

    public List<ElementInfo> extractRootElements(XSModel model) {
        validateModel(model);
        XSNamedMap elementMap = extractElementMapFromModel(model);

        List<ElementInfo> elements = new ArrayList<>();
        if (elementMap.isEmpty()) {
            logger.info("elementMap is empty.");
            return elements;
        }
        for (Object obj : elementMap.values()) {
            ElementInfo elementInfo = getElementInfo((XSElementDeclaration) obj);
            logger.info("Extracted element: {}", elementInfo.getQualifiedName());
            elements.add(elementInfo);
        }
        return elements;
    }

    public ElementInfo findRootElement(XSModel model, String elementName) {
        verifyModelAndElementName(model, elementName);
        XSNamedMap elementMap = extractElementMapFromModel(model);

        for (Object obj : elementMap.values()) {
            XSElementDeclaration declaration = (XSElementDeclaration) obj;
            if (declaration.getName().equals(elementName)) {
                return getElementInfo(declaration);
            }
        }

        XSElementDeclaration elementDeclaration = (XSElementDeclaration) elementMap.itemByName(null, elementName);
        if (isElementDeclarationNull(elementDeclaration, elementName)) {
            return null;
        }
        return getElementInfo(elementDeclaration);
    }

    public ElementInfo findRootElement(XSModel model, String elementName, String namespace) {
        verifyModelAndElementName(model, elementName);
        XSNamedMap elementMap = extractElementMapFromModel(model);
        XSElementDeclaration elementDeclaration = (XSElementDeclaration) elementMap.itemByName(namespace, elementName);
        if (isElementDeclarationNull(elementDeclaration, elementName)) {
            return null;
        }
        return getElementInfo(elementDeclaration);
    }


    private ElementInfo getElementInfo(XSElementDeclaration obj) {
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

    private void validateModel(XSModel model) {
        if (model == null) {
            logger.error("XSModel is null but cant be.");
            throw new XsdExtractorException("model is null.");
        }
    }

    private XSNamedMap extractElementMapFromModel(XSModel model) {
        XSNamedMap elementMap = model.getComponents(ELEMENT_DECLARATION);
        if (elementMap == null) {
            logger.error("Failed to extract elementMap from from model.");
            throw new XsdExtractorException("ElementMap is null");
        }
        return elementMap;
    }

    private void valdidateElementName(String elementName) {
        if (elementName == null) {
            logger.error("elementName in findRootElement is null.");
            throw new XsdExtractorException("elementName is findRootElement is null.");
        }
    }

    private boolean isElementDeclarationNull(XSElementDeclaration elementDeclaration, String elementName) {
        if (elementDeclaration == null) {
            logger.info("Element {} could not be found in schema.", elementName);
            return true;
        }
        return false;
    }

    private void verifyModelAndElementName(XSModel model, String elementName) {
        validateModel(model);
        valdidateElementName(elementName);
    }
}
