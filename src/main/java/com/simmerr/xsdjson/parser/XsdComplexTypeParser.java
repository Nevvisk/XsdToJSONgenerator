package com.simmerr.xsdjson.parser;

import com.simmerr.xsdjson.model.*;
import com.simmerr.xsdjson.parser.strategy.AllModelGroupStrategy;
import com.simmerr.xsdjson.parser.strategy.ChoiceModelGroupStrategy;
import com.simmerr.xsdjson.parser.strategy.ModelGroupStrategy;
import com.simmerr.xsdjson.parser.strategy.SequenceModelGroupStrategy;
import com.simmerr.xsdjson.parser.exceptions.XsdComplexTypeParsingException;
import org.apache.xerces.xs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Map;

public class XsdComplexTypeParser {

    protected XsdParsingHelper helper = new XsdParsingHelper();
    private static final Logger logger = LoggerFactory.getLogger(XsdComplexTypeParser.class);
    private final Map<Short, ModelGroupStrategy> modelGroupStrategies;

    public XsdComplexTypeParser() {
        this.modelGroupStrategies = Map.of(
                XSModelGroup.COMPOSITOR_SEQUENCE, new SequenceModelGroupStrategy(),
                XSModelGroup.COMPOSITOR_CHOICE, new ChoiceModelGroupStrategy(),
                XSModelGroup.COMPOSITOR_ALL, new AllModelGroupStrategy()
        );
    }

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
        ModelGroupStrategy strategy = modelGroupStrategies.get(group.getCompositor());
        if (strategy == null) {
            logger.error("Unsupported model group compositor value: {}", group.getCompositor());
            throw new XsdComplexTypeParsingException("Unsupported model group compositor: " + group.getCompositor());
        }
        logger.debug("Parsing model group compositor: {}", strategy.getClass().getSimpleName());
        return strategy.parse(group, typeRegistry, this::parseParticle);
    }
}
