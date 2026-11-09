package edu.smarthome.controller;

import edu.smarthome.model.Device;
import edu.smarthome.pattern.behavior.strategy.ClimateStrategy;
import edu.smarthome.pattern.structural.composite.DeviceGroup;
import edu.smarthome.service.SmartHomeFacade;

public class GroupController {

    private final SmartHomeFacade facade = new SmartHomeFacade();

    private DeviceGroup group;

    //Crea un grupo utilizando el Facade y el patrón Composite.
    public void createGroup(String name){
        group = facade.createGroup(name);
    }

    //Agrega un dispositivo al grupo.
    public void addDevice(Device device){
        if (group == null){
            throw new IllegalStateException("El grupo no ha sido creado");
        }

        facade.addDeviceToGroup(device, group);
    }

    //Retira un dispositivo del grupo.
    public void removeDevice(Device device){
        if (group == null){
            throw new IllegalStateException("El grupo no ha sido creado");
        }

        facade.removeDeviceFromGroup(device, group);
    }

    //Enciende o apaga todos los dispositivos del grupo.
    public void setPower(boolean on){
        if (group == null){
            throw new IllegalStateException("El grupo no ha sido creado");
        }

        facade.setGroupPower(group, on);
    }

    //Cambia la temperatura de los dispositivos del grupo.
    public void setTemperature(double temperature){
        if (group == null){
            throw new IllegalStateException("El grupo no ha sido creado");
        }

        facade.setGroupTemperature(group, temperature);
    }

    //Cambia el modo climático de los dispositivos del grupo.
    public void setClimateMode(ClimateStrategy strategy){
        if (group == null){
            throw new IllegalStateException("El grupo no ha sido creado");
        }

        facade.setGroupClimateMode(group, strategy);
    }

    //Bloquea los dispositivos del grupo.
    public void lockGroup(){
        if (group == null){
            throw new IllegalStateException("El grupo no ha sido creado");
        }

        facade.lockGroup(group);
    }

    public DeviceGroup getGroup(){
        return group;
    }
}
