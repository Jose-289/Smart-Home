package edu.smarthome.pattern.structural.composite;

import edu.smarthome.model.Device;
import edu.smarthome.pattern.behavior.command.Command;
import edu.smarthome.pattern.behavior.strategy.ClimateStrategy;

import java.util.List;

public interface DeviceComponent {
    String getName();
    int deviceCount();
    List<Device> devices();   // todos los dispositivos, aplanados

    default List<DeviceComponent> children() { return List.of(); }

    // Operaciones que se propagan por el árbol. Por defecto no hacen nada;
    // cada tipo de dispositivo sobrescribe solo las que le corresponden.
    default List<Command> powerCommands(boolean on)              { return List.of(); }
    default List<Command> temperatureCommands(double target)     { return List.of(); }
    default List<Command> climateModeCommands(ClimateStrategy s) { return List.of(); }
    default List<Command> lockCommands()                         { return List.of(); }
}