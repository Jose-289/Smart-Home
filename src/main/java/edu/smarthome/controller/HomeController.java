package edu.smarthome.controller;

import edu.smarthome.model.Light;
import edu.smarthome.model.SmartLock;
import edu.smarthome.model.Thermostat;
import edu.smarthome.pattern.structural.composite.DeviceGroup;
import edu.smarthome.service.SmartHomeFacade;
import javafx.fxml.FXML;

import javafx.scene.control.Label;

public class HomeController {

    private final SmartHomeFacade facade = new SmartHomeFacade();

    private Light light;
    private Thermostat thermostat;
    private SmartLock smartLock;
    private DeviceGroup homeGroup;

    @FXML
    private Label statusLabel;

    @FXML
    public void initialize(){

        //Crea los dispositivos utilizando el Facade.
        light = facade.createLight("Luz principal");
        thermostat = facade.createThermostat("Termostato principal", 22);
        smartLock = facade.createSmartLock("Cerradura principal","1234", "Alejandra", "1234");

        //Crea un grupo y agrega los dispositivos.
        homeGroup = facade.createGroup("Casa");

        facade.addDeviceToGroup(light, homeGroup);
        facade.addDeviceToGroup(thermostat, homeGroup);
        facade.addDeviceToGroup(smartLock, homeGroup);

        //Inicializa el Mediator con los dispositivos principales
        facade.initializeMediator();

        statusLabel.setText("Sistema listo");
    }

    @FXML
    private void onLightsClick(){
        facade.setGroupPower(homeGroup, true);
        statusLabel.setText("Grupo Casa activado");
    }

    @FXML
    private void onGroupsClick(){
        facade.setGroupPower(homeGroup, true);
        statusLabel.setText("Grupo Casa activado");
    }

    @FXML
    private void onUnlockHomeClick(){
        facade.unlockHome("1234");
        statusLabel.setText("Casa desbloqueada");
    }

    @FXML
    private void onLockHomeClick(){
        facade.lockHome();
        statusLabel.setText("Casa bloqueada");
    }

}
