package com.simmerr.xsdjson.parser;

import com.simmerr.xsdjson.model.*;
import com.simmerr.xsdjson.parser.exceptions.XsdComplexTypeParsingException;
import org.apache.xerces.xs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class XsdComplexTypeParser {

    protected XsdParsingHelper helper = new XsdParsingHelper();
    private static final Logger logger = LoggerFactory.getLogger(XsdComplexTypeParser.class);

    public ComplexTypeDefinition parseComplexType(XSComplexTypeDefinition type, TypeRegistry registry) {
        ComplexTypeDefinition complexTypeDefinition = new ComplexTypeDefinition();
        String typeName = type.getName();
        if (typeName == null || typeName.isEmpty()) {
            typeName = "AnonymousType";
        }
        String typeNamespace = type.getNamespace();
        if (typeNamespace == null) {
            typeNamespace = "";
        }
        complexTypeDefinition.setName(typeName);
        complexTypeDefinition.setNamespace(typeNamespace);
        complexTypeDefinition.setMixed(type.getContentType() == XSComplexTypeDefinition.CONTENTTYPE_MIXED);
        complexTypeDefinition.setAbstract(type.getAbstract());
        logger.debug("Parsing complex type {} (namespace={})", typeName, typeNamespace);

        XSParticle particle = type.getParticle();
        if (particle == null) {
            complexTypeDefinition.setContentModel(ContentModel.EMPTY);
            complexTypeDefinition.setChildElements(new ArrayList<>());
            registry.register(complexTypeDefinition);
            logger.debug("Complex type {} has empty content model", typeName);
            return complexTypeDefinition;
        }

        ParsedGroup parsedGroup = parseParticle(particle, registry);
        complexTypeDefinition.setContentModel(parsedGroup.getContentModel());
        complexTypeDefinition.setChildElements(parsedGroup.getElementList());
        registry.register(complexTypeDefinition);
        return complexTypeDefinition;
    }

    private ParsedGroup parseParticle(XSParticle particle, TypeRegistry typeRegistry) {
        if (null == particle.getTerm()) {
            throw new XsdComplexTypeParsingException("Term is null for particle.");
        }

        ParsedGroup parsedGroup = new ParsedGroup();
        XSTerm term = particle.getTerm();
        if (term instanceof XSModelGroup) {
            XSModelGroup group = (XSModelGroup) term;
            parsedGroup = parseModelGroup(group, typeRegistry);
        } else if (term instanceof XSElementDeclaration) {
            XSElementDeclaration elementDeclaration = (XSElementDeclaration) term;
            ElementInfo elementInfo = parseElementDeclaration(elementDeclaration, particle.getMinOccurs(), particle.getMaxOccurs(), typeRegistry);
            parsedGroup.setContentModel(ContentModel.SEQUENCE);
            parsedGroup.getElementList().add(elementInfo);
        } else {
            throw new XsdComplexTypeParsingException("Term is not of known instance");
        }
        return parsedGroup;
    }

    private ElementInfo parseElementDeclaration(XSElementDeclaration elementDeclaration, int minOccurs, int maxOccurs, TypeRegistry typeRegistry) {
        ElementInfo elementInfo = helper.getElementInfo(elementDeclaration);
        elementInfo.setMinOccurs(minOccurs);
        elementInfo.setMaxOccurs(maxOccurs);
        return elementInfo;
    }

    private ParsedGroup parseModelGroup(XSModelGroup group, TypeRegistry typeRegistry) {
        ParsedGroup parsedGroup = new ParsedGroup();
        if (group.getCompositor() == XSModelGroup.COMPOSITOR_SEQUENCE) {
            logger.debug("Parsing model group compositor: SEQUENCE");
            parsedGroup.setContentModel(ContentModel.SEQUENCE);
            List<XSParticle> particleList = getParticleListFromGroup(group);
            for (XSParticle particle : particleList) {
                ParsedGroup childGroup = parseParticle(particle, typeRegistry);
                parsedGroup.getElementList().addAll(childGroup.getElementList());
            }
        } else if (group.getCompositor() == XSModelGroup.COMPOSITOR_CHOICE) {
            logger.debug("Parsing model group compositor: CHOICE");
            parsedGroup.setContentModel(ContentModel.CHOICE);
            List<XSParticle> particleList = getParticleListFromGroup(group);
            for (XSParticle particle : particleList) {
                ParsedGroup childGroup = parseParticle(particle, typeRegistry);
                parsedGroup.getElementList().addAll(childGroup.getElementList());
            }
        } else if (group.getCompositor() == XSModelGroup.COMPOSITOR_ALL) {
            logger.debug("Parsing model group compositor: ALL");
            parsedGroup.setContentModel(ContentModel.ALL);
            List<XSParticle> particleList = getParticleListFromGroup(group);
            for (XSParticle particle : particleList) {
                ParsedGroup childGroup = parseParticle(particle, typeRegistry);
                parsedGroup.getElementList().addAll(childGroup.getElementList());
            }
        } else {
            logger.error("Unsupported model group compositor value: {}", group.getCompositor());
            throw new XsdComplexTypeParsingException("Unsupported model group compositor: " + group.getCompositor());
        }
        return parsedGroup;
    }

    private List<XSParticle> getParticleListFromGroup(XSModelGroup group) {
        List<XSParticle> particleList = new ArrayList<>();
        if (group != null) {
            for (int i = 0; i < group.getParticles().getLength(); i++) {
                XSParticle particle = (XSParticle) group.getParticles().get(i);
                particleList.add(particle);
            }
        }
        return particleList;
    }
}
