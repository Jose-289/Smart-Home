package edu.smarthome.controller;

import edu.smarthome.model.Light;
import edu.smarthome.service.SmartHomeFacade;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class LightController {

    private SmartHomeFacade facade;
    private Light light;

    @FXML
    private Label statusLabel;

    @FXML
    private TextField brightnessField;

    @FXML
    public void initialize(){

    }

    public void  configure(SmartHomeFacade facade, Light light){
        this.facade = facade;
        this.light = light;

        updateView();
    }

    //Enciende la luz utilizando el Facade y Command.
    @FXML
    private void onTurnOnClick(){

        facade.setLightPower(light, true);

        updateView();
    }

    //Apaga la luz utilizando el Facade y Command.
    @FXML
    private void onTurnOffClick(){

        facade.setLightPower(light, false);

        updateView();
    }

    //Cambia el brillo de la luz utilizando el Facade y Command.
    @FXML
    private void onSetBrightnessClick(){

        try {
            int brightness = Integer.parseInt(brightnessField.getText());

            facade.setLightBrightness(light, brightness);

            updateView();

        }catch (NumberFormatException e){

            statusLabel.setText("Ingrese un brillo válido");

        }catch (IllegalArgumentException e){

            statusLabel.setText(e.getMessage());
        }
    }

    private void updateView(){

        if (light == null){
            return;
        }

        String status;

        if (light.isOn()){
            status = "Luz encendida";
        }else {
            status = "Luz apagada";
        }

        statusLabel.setText(status + " | Brillo: " + light.getBrightness() + "%");
    }

    //Deshace la última operación.
    @FXML
    private void onUndoClick(){

        if (facade.canUndo()){
            facade.undo();
            updateView();
        }
    }

    //Rehace la última operación.
    @FXML
    private void onRedoClick(){

        if (facade.canRedo()){
            facade.redo();
            updateView();
        }
    }

    public Light getLight(){
        return light;
    }
}
