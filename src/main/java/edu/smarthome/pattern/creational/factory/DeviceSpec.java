package edu.smarthome.pattern.creational.factory;

import java.util.HashMap;
import java.util.Map;

public record DeviceSpec(String name, Map<String, String> options) {

    public DeviceSpec {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("El nombre es obligatorio");
        options = (options == null) ? Map.of() : Map.copyOf(options);
    }

    public static DeviceSpec of(String name) {
        return new DeviceSpec(name.trim(), Map.of());
    }

    public DeviceSpec with(String key, String value) {
        Map<String, String> copy = new HashMap<>(options);
        copy.put(key, value);
        return new DeviceSpec(name, copy);
    }

    public String option(String key, String defaultValue) {
        return options.getOrDefault(key, defaultValue);
    }

    public String required(String key) {
        String v = options.get(key);
        if (v == null || v.isBlank())
            throw new IllegalArgumentException("Falta el dato: " + key);
        return v;
    }
}