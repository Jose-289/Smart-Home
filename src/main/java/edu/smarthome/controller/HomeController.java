package edu.smarthome.controller;

import edu.smarthome.app.SmartHomeApplication;
import edu.smarthome.model.Light;
import edu.smarthome.model.SmartLock;
import edu.smarthome.model.Thermostat;
import edu.smarthome.pattern.structural.composite.DeviceGroup;
import edu.smarthome.service.SmartHomeFacade;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

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

        try {
            FXMLLoader loader = new FXMLLoader(SmartHomeApplication.class.getResource(
                    "/edu/smarthome/view/light-view.fxml"));

            Scene scene = new Scene(loader.load());

            LightController controller = loader.getController();
            controller.configure(facade, light);

            Stage stage = new Stage();
            stage.setTitle("Luces");
            stage.setScene(scene);
            stage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
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

    @FXML
    private void onThermostatClick(){

        try {
            FXMLLoader loader = new FXMLLoader(SmartHomeApplication.class.getResource(
                    "/edu/smarthome/view/thermostat-view.fxml"));

            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.setTitle("Termostato");
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
