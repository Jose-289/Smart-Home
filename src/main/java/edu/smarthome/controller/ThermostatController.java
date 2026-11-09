package edu.smarthome.controller;

import edu.smarthome.model.Thermostat;
import edu.smarthome.pattern.behavior.strategy.ClimateStrategy;
import edu.smarthome.service.SmartHomeFacade;

public class ThermostatController {

    private final SmartHomeFacade facade = new SmartHomeFacade();

    private Thermostat thermostat;

    //Crea el termostato utilizando el Facade y el patrón Factory.
    public void createThermostat(String name, double temperature){
        thermostat = facade.createThermostat(name, temperature);
    }

    //Cambia la temperatura utilizando el Facade y el patrón Command.
    public void setTemperature(double temperature){
        if (thermostat == null){
            throw new IllegalStateException("El termostato no ha sido creado");
        }

        facade.setThermostatTemperature(thermostat, temperature);
    }

    //Cambia el modo climático utilizando el patrón Strategy.
    public void setClimaMode(ClimateStrategy strategy){
        if (thermostat == null){
            throw new IllegalStateException("El termostato no ha sido creado");
        }

        facade.setThermostatClimateMode(thermostat, strategy);
    }

    public Thermostat getThermostat(){
        return thermostat;
    }
}
