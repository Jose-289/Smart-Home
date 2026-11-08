package edu.smarthome.pattern.creational.factory;

import edu.smarthome.model.Device;
import edu.smarthome.model.Thermostat;
import edu.smarthome.pattern.behavior.observer.DeviceObserver;
import edu.smarthome.pattern.behavior.strategy.ComfortStrategy;

import java.util.List;

public class ThermostatCreator extends DeviceCreator {
    public ThermostatCreator(List<DeviceObserver> observers) { super("thermo", observers); }

    @Override
    protected Device build(String id, DeviceSpec spec) {
        double initial;
        try {
            initial = Double.parseDouble(spec.option("initialTemperature", "22"));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La temperatura inicial no es un número válido");
        }
        return new Thermostat(id, spec.name(), initial, new ComfortStrategy());
    }
}