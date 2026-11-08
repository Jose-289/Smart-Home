package edu.smarthome.pattern.creational.factory;

public enum DeviceType {
    LIGHT("Luz"),
    THERMOSTAT("Termostato"),
    LOCK("Cerradura");

    private final String label;
    DeviceType(String label) { this.label = label; }
    public String label() { return label; }

    @Override public String toString() { return label; } // para mostrarlo en un ComboBox
}