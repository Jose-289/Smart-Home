package edu.smarthome.service;

import edu.smarthome.model.Device;
import edu.smarthome.model.Light;
import edu.smarthome.model.SmartLock;
import edu.smarthome.model.Thermostat;
import edu.smarthome.pattern.behavior.command.Command;
import edu.smarthome.pattern.behavior.command.CommandHistory;
import edu.smarthome.pattern.behavior.command.MacroCommand;
import edu.smarthome.pattern.behavior.command.SetBrightnessCommand;
import edu.smarthome.pattern.behavior.mediator.HomeMediator;
import edu.smarthome.pattern.behavior.mediator.SmartHomeMediator;
import edu.smarthome.pattern.behavior.observer.DeviceObserver;
import edu.smarthome.pattern.behavior.strategy.ClimateStrategy;
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

    //Crea dispositivos utilizando el patrón Factory
    public Light createLight(String name){
        Light light = (Light) lightCreator.create(DeviceSpec.of(name));
        devices.add(light);
        return light;
    }

    //Crea un termostato utilizando el patrón Factory
    public Thermostat createThermostat(String name, double initialTemperature){
        DeviceSpec spec = DeviceSpec.of(name).with("initialTemperature", String.valueOf(initialTemperature));

        Thermostat thermostat = (Thermostat) thermostatCreator.create(spec);
        devices.add(thermostat);
        return thermostat;
    }

    //Crea una cerradura inteligente utilizando el patrón Factory
    public SmartLock createSmartLock(String name, String masterPin, String firstUser, String firstPin){
        DeviceSpec spec = DeviceSpec.of(name).with("masterPin", masterPin).with("firstUser", firstUser)
                .with("firstPin", firstPin);

        SmartLock smartLock = (SmartLock) smartLockCreator.create(spec);
        devices.add(smartLock);
        return smartLock;
    }

    //busca la primera luz creada.
    public Light findLight(){
        for (Device device : devices){
            if (device instanceof Light){
                return (Light) device;
            }
        }
        throw new IllegalStateException("No light has been created");
    }

    //Busca el primer termostato creado.
    public Thermostat findThermostat(){
        for (Device device : devices){
            if (device instanceof Thermostat){
                return (Thermostat) device;
            }
        }
        throw new IllegalStateException("No thermostat has been created");
    }

    //Busca la primera cerradura inteligente creada.
    public SmartLock findSmartLock(){
        for (Device device : devices){
            if (device instanceof SmartLock){
                return (SmartLock) device;
            }
        }
        throw new IllegalStateException("No smart lock has been created");
    }

    //Inicializa el Mediator con los dispositivos principales de la casa.
    public void initializeMediator(){
        this.mediator = new SmartHomeMediator(findSmartLock(), findLight(), findThermostat());
    }

    //Delega al Mediator la coordinación para desbloquear la casa.
    public void unlockHome(String pin){
        if (mediator == null){
            throw new IllegalStateException("Mediator has not been initialized");
        }

        mediator.unlockHome(pin);
    }

    //Delega al Mediator la coordinación para bloquear la casa.
    public void lockHome(){
        if (mediator == null){
            throw new IllegalStateException("Mediator has not been initialized");
        }

        mediator.lockHome();
    }

    //Crea un grupo de dispositivos utilizando el patrón Composite.
    public DeviceGroup createGroup(String name){
        DeviceGroup group = new DeviceGroup(name);
        groups.add(group);
        return group;
    }

    //Agrega un dispositivo al grupo.
    public void addDeviceToGroup(Device device, DeviceGroup group){
        group.add(device);
    }

    //Retira un dispositivo del grupo.
    public void removeDeviceFromGroup(Device device, DeviceGroup group){
        group.remove(device);
    }

    //Crea y ejecuta los comandos para encender o apagar todos los dispositivos del grupo.
    public void setGroupPower(DeviceGroup group, boolean on){

        List<Command> commands = group.powerCommands(on);

        if (commands.isEmpty()){
            return;
        }

        String description;

        if (on){
            description = "Turn on " + group.getName();
        }else {
            description = "Turn off " + group.getName();
        }

        MacroCommand macro = new MacroCommand(description, commands);

        history.execute(macro);
    }

    //Crea y ejecuta los comandos para cambiar la temperatura del grupo.
    public void setGroupTemperature(DeviceGroup group, double temperature){

        List<Command> commands = group.temperatureCommands(temperature);

        if (commands.isEmpty()){
            return;
        }

        MacroCommand macro = new MacroCommand("Set temperature " + temperature + " for " + group.getName(), commands);

        history.execute(macro);
    }

    //Crea y ejecuta los comandos para cambiar el modo climático del grupo.
    public void setGroupClimateMode(DeviceGroup group, ClimateStrategy strategy){

        List<Command> commands = group.climateModeCommands(strategy);

        if (commands.isEmpty()){
            return;
        }

        String description = "Set climate mode for " + group.getName();

        MacroCommand macro = new MacroCommand(description, commands);

        history.execute(macro);
    }

    //Crea y ejecuta los comandos para bloquear los dispositivos del grupo.
    public void lockGroup(DeviceGroup group){

        List<Command> commands = group.lockCommands();

        if (commands.isEmpty()){
            return;
        }

        String description = "Lock " + group.getName();

        MacroCommand macro = new MacroCommand(description, commands);

        history.execute(macro);
    }

    //Crea y ejecuta el comando para encender o apagar una luz.
    public void setLightPower(Light light, boolean on){

        List<Command> commands = light.powerCommands(on);

        if (commands.isEmpty()){
            return;
        }

        String description;

        if (on){
            description = "Turn on " + light.getName();
        }else {
            description = "Turn off " + light.getName();
        }

        MacroCommand macro = new MacroCommand(description, commands);

        history.execute(macro);
    }

    //Crea y ejecuta el comando para cambiar el brillo de una luz.
    public void setLightBrightness(Light light, int brightness){

       List<Command> commands = light.brightnessCommands(brightness);

       if (commands.isEmpty()){
           return;
       }

       MacroCommand macro = new MacroCommand("Set brightness to " + brightness + "% for " + light.getName(), commands);

       history.execute(macro);
    }

    //Crea y ejecuta el comando para cambiar la temperatura de un termostato.
    public void setThermostatTemperature(Thermostat thermostat, double temperature){

        List<Command> commands = thermostat.temperatureCommands(temperature);

        if (commands.isEmpty()){
            return;
        }

        MacroCommand macro = new MacroCommand("Set thermostat temperature to " + temperature, commands);

        history.execute(macro);
    }

    //Crea y ejecuta el comando para cambiar el modo climático de un termostato.
    public void setThermostatClimateMode(Thermostat thermostat, ClimateStrategy strategy){

        List<Command> commands = thermostat.climateModeCommands(strategy);

        if (commands.isEmpty()){
            return;
        }

        MacroCommand macro = new MacroCommand("Set thermostat climate mode", commands);

        history.execute(macro);
    }

    //Permite deshacer la última operación realizada.
    public void undo(){
        history.undo();
    }

    //Permite rehacer la última operación deshecha.
    public void redo(){
        history.redo();
    }

    //Indica si existe una operación que se pueda deshacer.
    public boolean canUndo(){
        return history.canUndo();
    }

    //Indica si existe una operación que se pueda rehacer.
    public boolean canRedo(){
        return history.canRedo();
    }
}
