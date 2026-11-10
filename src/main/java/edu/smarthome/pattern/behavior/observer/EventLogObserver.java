package edu.smarthome.pattern.behavior.observer;

import edu.smarthome.model.DeviceEvent;

public class EventLogObserver implements DeviceObserver{

    @Override
    public void onDeviceEvent(DeviceEvent event){
        System.out.println("[EVENT] " +
                event.deviceID() +
                ": " +
                event.message());
    }
}
