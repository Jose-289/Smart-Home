package edu.smarthome.controller;

import edu.smarthome.model.Light;
import edu.smarthome.model.SmartLock;
import edu.smarthome.model.Thermostat;
import edu.smarthome.service.SmartHomeFacade;

public class DeviceController {

    private final SmartHomeFacade facade = new SmartHomeFacade();

    //Crea una luz utilizando el Facade y el patrón Factory.
    public Light createLight(String name){
        return facade.createLight(name);
    }

    //Crea un termostato utilizando el Facade y el patrón Factory.
    public Thermostat createThermostat(String name, double temperature){
        return facade.createThermostat(name, temperature);
    }

    //Crea una cerradura utilizando el Facade y el patrón Factory.
    public SmartLock createSmartLock(String name, String masterPin, String firstUser, String firstPin){
        return facade.createSmartLock(name, masterPin, firstUser, firstPin);
    }
}
