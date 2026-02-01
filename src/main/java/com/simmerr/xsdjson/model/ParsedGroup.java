package com.simmerr.xsdjson.model;

import java.util.ArrayList;
import java.util.List;

public class ParsedGroup {

    private ContentModel contentModel;
    private List<ElementInfo> elementList;

    public ParsedGroup(ContentModel contentModel, List<ElementInfo> elementList) {
        this.contentModel = contentModel;
        this.elementList = elementList;
    }

    public ParsedGroup() {
        elementList = new ArrayList<>();
    }

    public ContentModel getContentModel() {
        return contentModel;
    }

    public void setContentModel(ContentModel contentModel) {
        this.contentModel = contentModel;
    }

    public List<ElementInfo> getElementList() {
        return elementList;
    }

    public void setElementList(List<ElementInfo> elementList) {
        this.elementList = elementList;
    }
}
