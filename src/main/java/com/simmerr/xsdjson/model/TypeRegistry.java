package com.simmerr.xsdjson.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class TypeRegistry {
    private static final Logger logger = LoggerFactory.getLogger(TypeRegistry.class);

    private static TypeRegistry instance;

    private TypeRegistry() {

    }

    public static TypeRegistry getInstance() {
        if (instance == null) {
            instance = new TypeRegistry();
        }
        return instance;
    }

    private final Map<String, TypeDefinition> registry = new HashMap<>();

    public void register(TypeDefinition typeDefinition) {
        String key = createKey(typeDefinition.getName(), typeDefinition.getNamespace());
        if (registry.containsKey(key)) {
            logger.error("Type {} already exists in registry", key);
            throw new IllegalArgumentException("Key " + typeDefinition.getName() + " is already in the registry.");
        }
        registry.put(key, typeDefinition);
        logger.debug("Registered type {}", key);
    }

    public TypeDefinition get(String name, String namespace) {
        TypeDefinition returnDefinition = registry.get(createKey(name, namespace));
        if (returnDefinition == null) {
            throw new IllegalArgumentException("Key " + name + " is not in the registry.");
        }
        return returnDefinition;
    }

    public boolean has(String name, String namespace) {
        return registry.containsKey(createKey(name, namespace));
    }

    public void unregister(String name, String namespace) {
        String key = createKey(name, namespace);
        if (!registry.containsKey(key)) {
            throw new IllegalArgumentException("Key " + name + " is not in the registry.");
        }
        registry.remove(key);
    }

    public void clear() {
        logger.debug("Clearing type registry with {} entries", registry.size());
        registry.clear();
    }

    private String createKey(String name, String namespace) {
        if (namespace == null || namespace.isEmpty()) {
            return name;
        }
        return "{" + namespace + "}" + name;
    }

    private String createKey(TypeDefinition typeDefinition) {
        return createKey(typeDefinition.getName(), typeDefinition.getNamespace());
    }
}
