package edu.smarthome.pattern.behavior.observer;

import edu.smarthome.model.DeviceEvent;

public interface DeviceObserver {
    void onDeviceEvent(DeviceEvent event);
}
