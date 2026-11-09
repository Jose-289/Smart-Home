package edu.smarthome.pattern.behavior.mediator;

import edu.smarthome.model.Light;
import edu.smarthome.model.SmartLock;
import edu.smarthome.model.Thermostat;

public class SmartHomeMediator implements HomeMediator{

    private final SmartLock smartLock;
    private final Light light;
    private final Thermostat thermostat;

    public SmartHomeMediator(SmartLock smartLock, Light light, Thermostat thermostat){
        this.smartLock = smartLock;
        this.light = light;
        this.thermostat = thermostat;
    }

    @Override
    public void unlockHome(String pin){
        smartLock.unlock(pin);
        light.setOn(true);
        thermostat.setTargetTemperature(22);
    }

    @Override
    public void lockHome(){
        smartLock.lock();
        light.setOn(false);
    }
}
