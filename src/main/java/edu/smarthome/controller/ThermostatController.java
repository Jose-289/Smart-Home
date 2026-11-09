package edu.smarthome.controller;

import edu.smarthome.model.Thermostat;
import edu.smarthome.pattern.behavior.strategy.ClimateStrategy;
import edu.smarthome.pattern.behavior.strategy.ComfortStrategy;
import edu.smarthome.pattern.behavior.strategy.EcoStrategy;
import edu.smarthome.pattern.behavior.strategy.NightStrategy;
import edu.smarthome.service.SmartHomeFacade;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class ThermostatController {

    private final SmartHomeFacade facade = new SmartHomeFacade();

    private Thermostat thermostat;

    @FXML
    private TextField temperatureField;

    @FXML
    private Label currentTemperatureLabel;

    @FXML
    private Label targetTemperatureLabel;

    @FXML
    private Label modeLabel;

    @FXML
    private Label statusLabel;

    @FXML
    public void initialize(){

        //Crea el termostato utilizando el Facade y Factory.
        createThermostat("Termostato principal", 22);

        updateView();
    }

    //Crea el termostato.
    public void createThermostat(String name, double temperature){
        thermostat = facade.createThermostat(name, temperature);
    }

    //Cambia la temperatura utilizando el Facade y Command.
    public void setTemperature(double temperature){
        if (thermostat == null){
            throw new IllegalStateException("El termostato no ha sido creado");
        }

        facade.setThermostatTemperature(thermostat, temperature);
    }

    //Cambia el modo climático utilizando Facade, Strategy y Command.
    public void setClimaMode(ClimateStrategy strategy){
        if (thermostat == null){
            throw new IllegalStateException("El termostato no ha sido creado");
        }

        facade.setThermostatClimateMode(thermostat, strategy);
    }

    @FXML
    private void onSetTemperatureClick(){

        try {
            double temperature = Double.parseDouble(temperatureField.getText());

            setTemperature(temperature);

            updateView();

            statusLabel.setText("Temperatura actualizada");
        }catch (NumberFormatException e){

            statusLabel.setText("Ingrese una temperatura válida");
        }catch (IllegalArgumentException e){

            statusLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void onComfortClick(){

        setClimaMode(new ComfortStrategy());

        updateView();

        statusLabel.setText("Modo Comfort seleccionado");
    }

    @FXML
    private void onEcoClick(){

        setClimaMode(new EcoStrategy());

        updateView();

        statusLabel.setText("Modo Eco seleccionado");
    }

    @FXML
    private void onNightClick(){

        setClimaMode(new NightStrategy());

        updateView();

        statusLabel.setText("Modo Night seleccionado");
    }

    private void updateView(){

        if (thermostat == null){
            return;
        }

        currentTemperatureLabel.setText(String.format("Temperatura actual: %.1f °C", thermostat.getCurrentTemperature()));

        targetTemperatureLabel.setText(String.format("Temperatura objetivo: %.1f °C", thermostat.getTargetTemperature()));

        modeLabel.setText("Modo: " + thermostat.getStrategy().name());

    }

    public Thermostat getThermostat(){
        return thermostat;
    }
}
