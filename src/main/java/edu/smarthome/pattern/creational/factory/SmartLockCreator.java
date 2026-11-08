package edu.smarthome.pattern.creational.factory;

import edu.smarthome.model.Device;
import edu.smarthome.model.SmartLock;
import edu.smarthome.pattern.behavior.observer.DeviceObserver;

import java.util.List;

public class SmartLockCreator extends DeviceCreator {
    public SmartLockCreator(List<DeviceObserver> observers) { super("lock", observers); }

    @Override
    protected Device build(String id, DeviceSpec spec) {
        return new SmartLock(id, spec.name(),
                spec.required("masterPin"),
                spec.required("firstUser"),
                spec.required("firstPin"));   // SmartLock ya valida el formato del PIN
    }
}