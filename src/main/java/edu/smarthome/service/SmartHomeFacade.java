package edu.smarthome.service;

import edu.smarthome.model.Device;
import edu.smarthome.model.Light;
import edu.smarthome.model.SmartLock;
import edu.smarthome.model.Thermostat;
import edu.smarthome.pattern.behavior.command.CommandHistory;
import edu.smarthome.pattern.behavior.mediator.HomeMediator;
import edu.smarthome.pattern.behavior.mediator.SmartHomeMediator;
import edu.smarthome.pattern.behavior.observer.DeviceObserver;
import edu.smarthome.pattern.creational.factory.DeviceSpec;
import edu.smarthome.pattern.creational.factory.LightCreator;
import edu.smarthome.pattern.creational.factory.SmartLockCreator;
import edu.smarthome.pattern.creational.factory.ThermostatCreator;
import edu.smarthome.pattern.structural.composite.DeviceGroup;

import java.util.ArrayList;
import java.util.List;

public class SmartHomeFacade {

    private final List<Device> devices = new ArrayList<>();
    private final List<DeviceGroup> groups = new ArrayList<>();

    private final CommandHistory history = new CommandHistory();

    private final LightCreator lightCreator;
    private final ThermostatCreator thermostatCreator;
    private final SmartLockCreator smartLockCreator;

    private HomeMediator mediator;

    public SmartHomeFacade(){
        List<DeviceObserver> observers = List.of();

        this.lightCreator = new LightCreator(observers);
        this.thermostatCreator = new ThermostatCreator(observers);
        this.smartLockCreator = new SmartLockCreator(observers);
    }

    public Light createLight(String name){
        Light light = (Light) lightCreator.create(DeviceSpec.of(name));
        devices.add(light);
        return light;
    }

    public Thermostat createThermostat(String name, double initialTemperature){
        DeviceSpec spec = DeviceSpec.of(name).with("initialTemperature", String.valueOf(initialTemperature));

        Thermostat thermostat = (Thermostat) thermostatCreator.create(spec);
        devices.add(thermostat);
        return thermostat;
    }

    public SmartLock createSmartLock(String name, String masterPin, String firstUser, String firstPin){
        DeviceSpec spec = DeviceSpec.of(name).with("masterPin", masterPin).with("firstUser", firstUser)
                .with("firstPin", firstPin);

        SmartLock smartLock = (SmartLock) smartLockCreator.create(spec);
        devices.add(smartLock);
        return smartLock;
    }

    public Light findLight(){
        for (Device device : devices){
            if (device instanceof Light){
                return (Light) device;
            }
        }
        throw new IllegalStateException("No light has been created");
    }

    public Thermostat findThermostat(){
        for (Device device : devices){
            if (device instanceof Thermostat){
                return (Thermostat) device;
            }
        }
        throw new IllegalStateException("No thermostat has been created");
    }

    public SmartLock findSmartLock(){
        for (Device device : devices){
            if (device instanceof SmartLock){
                return (SmartLock) device;
            }
        }
        throw new IllegalStateException("No smart lock has been created");
    }

    public void initializeMediator(){
        this.mediator = new SmartHomeMediator(findSmartLock(), findLight(), findThermostat());
    }

    public void unlockHome(String pin){
        if (mediator == null){
            throw new IllegalStateException("Mediator has not been initialized");
        }

        mediator.unlockHome(pin);
    }

    public void lockHome(){
        if (mediator == null){
            throw new IllegalStateException("Mediator has not been initialized");
        }

        mediator.lockHome();
    }

    public void undo(){
        history.undo();
    }

    public void redo(){
        history.redo();
    }

    public boolean canUndo(){
        return history.canUndo();
    }

    public boolean canRedo(){
        return history.canRedo();
    }
}
