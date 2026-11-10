package edu.smarthome.pattern.structural.composite;

import edu.smarthome.model.Device;
import edu.smarthome.pattern.behavior.command.Command;
import edu.smarthome.pattern.behavior.strategy.ClimateStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class DeviceGroup implements DeviceComponent {

    private final String name;
    private final List<DeviceComponent> children = new ArrayList<>();

    public DeviceGroup(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("El nombre es obligatorio");
        this.name = name.trim();
    }

    public void add(DeviceComponent c) {
        if (c == this || (c instanceof DeviceGroup g && g.contains(this)))
            throw new IllegalArgumentException("Un grupo no puede contenerse a sí mismo");
        if (contains(c))
            throw new IllegalArgumentException(c.getName() + " ya está en " + name);
        children.add(c);
    }

    public void remove(DeviceComponent c) { children.remove(c); }

    public boolean contains(DeviceComponent c) {
        for (DeviceComponent child : children) {
            if (child == c) return true;
            if (child instanceof DeviceGroup g && g.contains(c)) return true;
        }
        return false;
    }

    @Override public String getName() { return name; }
    @Override public List<DeviceComponent> children() { return Collections.unmodifiableList(children); }

    @Override public int deviceCount() {
        return children.stream().mapToInt(DeviceComponent::deviceCount).sum();
    }

    @Override public List<Device> devices() {
        List<Device> all = new ArrayList<>();
        children.forEach(c -> all.addAll(c.devices()));
        return all;
    }

    // Cada operación se delega a los hijos (hojas u otros grupos) y se juntan los resultados
    @Override public List<Command> powerCommands(boolean on)              { return gather(c -> c.powerCommands(on)); }
    @Override
    public List<Command> brightnessCommands(int brightness){
        return gather(c -> c.brightnessCommands(brightness));
    }
    @Override public List<Command> temperatureCommands(double t)          { return gather(c -> c.temperatureCommands(t)); }
    @Override public List<Command> climateModeCommands(ClimateStrategy s) { return gather(c -> c.climateModeCommands(s)); }
    @Override public List<Command> lockCommands()                         { return gather(DeviceComponent::lockCommands); }

    private List<Command> gather(Function<DeviceComponent, List<Command>> op) {
        List<Command> result = new ArrayList<>();
        children.forEach(c -> result.addAll(op.apply(c)));
        return result;
    }

    @Override public String toString() { return name + " (" + deviceCount() + ")"; }
}