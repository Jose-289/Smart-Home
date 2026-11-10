package edu.smarthome.controller;

import edu.smarthome.model.Device;
import edu.smarthome.pattern.behavior.strategy.ClimateStrategy;
import edu.smarthome.pattern.behavior.strategy.ComfortStrategy;
import edu.smarthome.pattern.behavior.strategy.EcoStrategy;
import edu.smarthome.pattern.behavior.strategy.NightStrategy;
import edu.smarthome.pattern.structural.composite.DeviceGroup;
import edu.smarthome.service.SmartHomeFacade;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class GroupController {

    private SmartHomeFacade facade;

    private DeviceGroup group;

    @FXML
    private TextField temperatureField;

    @FXML
    Label statusLabel;

    public void configure(SmartHomeFacade facade, DeviceGroup group){
        this.facade = facade;
        this.group = group;
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

    @FXML
    private void onPowerOnClick(){
        setPower(true);
        statusLabel.setText("Grupo activado");
    }

    @FXML
    private void onPowerOffClick(){
        setPower(false);
        statusLabel.setText("Grupo desactivado");
    }

    @FXML
    private void onSetTemperatureClick(){

        try {
            double temperature = Double.parseDouble(temperatureField.getText());

            setTemperature(temperature);

            statusLabel.setText("Temperatura del grupo actualizada");

        }catch (NumberFormatException e){

            statusLabel.setText("Ingrese una temperatura válida");

        }catch (IllegalArgumentException e){

            statusLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void onComfortClick(){
        setClimateMode(new ComfortStrategy());
        statusLabel.setText("Modo Comfort seleccionado");
    }

    @FXML
    private void  onEcoClick(){
        setClimateMode(new EcoStrategy());
        statusLabel.setText("Modo Eco seleccionado");
    }

    @FXML
    private void onNightClick(){
        setClimateMode(new NightStrategy());
        statusLabel.setText("Modo Night seleccionado");
    }

    @FXML
    private void onLockGroupClick(){
        lockGroup();
        statusLabel.setText("Grupo bloqueado");
    }

    public DeviceGroup getGroup(){
        return group;
    }
}
