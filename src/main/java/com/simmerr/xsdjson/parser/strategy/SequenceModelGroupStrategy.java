package com.simmerr.xsdjson.parser.strategy;

import com.simmerr.xsdjson.model.ContentModel;
import com.simmerr.xsdjson.model.ParsedGroup;
import com.simmerr.xsdjson.model.TypeRegistry;
import org.apache.xerces.xs.XSModelGroup;
import org.apache.xerces.xs.XSParticle;

public class SequenceModelGroupStrategy implements ModelGroupStrategy {
    @Override
    public short compositor() {
        return XSModelGroup.COMPOSITOR_SEQUENCE;
    }

    @Override
    public ParsedGroup parse(XSModelGroup group, TypeRegistry registry, ModelGroupContext context) {
        ParsedGroup parsedGroup = new ParsedGroup();
        parsedGroup.setContentModel(ContentModel.SEQUENCE);
        for (int i = 0; i < group.getParticles().getLength(); i++) {
            XSParticle particle = (XSParticle) group.getParticles().item(i);
            parsedGroup.getElementList().addAll(context.parseParticle(particle, registry).getElementList());
        }
        return parsedGroup;
    }
}
