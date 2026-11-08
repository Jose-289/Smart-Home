package edu.smarthome.pattern.creational.factory;

import edu.smarthome.model.Device;
import edu.smarthome.model.Light;
import edu.smarthome.pattern.behavior.observer.DeviceObserver;

import java.util.List;

public class LightCreator extends DeviceCreator {
    public LightCreator(List<DeviceObserver> observers) { super("light", observers); }

    @Override
    protected Device build(String id, DeviceSpec spec) {
        return new Light(id, spec.name());
    }
}