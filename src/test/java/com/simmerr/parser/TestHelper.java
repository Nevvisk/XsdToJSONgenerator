package com.simmerr.parser;

import com.simmerr.xsdjson.model.ElementInfo;

import java.util.List;

public class TestHelper {

    public static ElementInfo findElementByName(List<ElementInfo> elementInfoList, String name) {
        return elementInfoList.stream()
                .filter(e -> e.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public static String getResourcePath(String filename) {
        return "src/test/resources/" + filename;
    }
}
