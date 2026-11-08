package edu.smarthome.pattern.creational.factory;

import edu.smarthome.model.Device;
import edu.smarthome.pattern.behavior.observer.DeviceObserver;

import java.util.List;

public abstract class DeviceCreator {

    private final String idPrefix;
    private final List<DeviceObserver> defaultObservers;
    private int counter = 0;

    protected DeviceCreator(String idPrefix, List<DeviceObserver> defaultObservers) {
        this.idPrefix = idPrefix;
        this.defaultObservers = List.copyOf(defaultObservers);
    }

    /** MÉTODO FÁBRICA: cada subclase decide qué dispositivo concreto construye. */
    protected abstract Device build(String id, DeviceSpec spec);

    /** Flujo común: construye y deja el dispositivo conectado a los observers. */
    public final Device create(DeviceSpec spec) {
        int next = counter + 1;
        Device device = build(idPrefix + "-" + next, spec); // si lanza excepción, no se consume el id
        counter = next;
        defaultObservers.forEach(device::addObserver);
        return device;
    }
}