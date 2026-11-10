package edu.smarthome.controller;

import edu.smarthome.model.SmartLock;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SmartLockController {

    private SmartLock smartLock;

    @FXML
    private PasswordField pinField;

    @FXML
    private TextField userField;

    @FXML
    private PasswordField newPinField;

    @FXML
    private Label stateLabel;

    @FXML
    private Label attemptsLabel;

    @FXML
    private Label statusLabel;

    /**
     * Permite inyectar la instancia de la cerradura desde el controlador principal.
     */
    public void setSmartLock(SmartLock smartLock) {
        this.smartLock = smartLock;
        updateView();
    }

    @FXML
    private void onUnlockClick() {
        if (smartLock == null) {
            statusLabel.setText("La cerradura no se ha inicializado");
            return;
        }

        try {
            String pin = pinField.getText();
            smartLock.unlock(pin);
            updateView();

            if ("UNLOCKED".equals(smartLock.getStateName())) {
                statusLabel.setText("¡Cerradura abierta con éxito!");
            } else {
                statusLabel.setText("PIN incorrecto. Intentos fallidos: " + smartLock.getFailedAttempts());
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            statusLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void onLockClick() {
        if (smartLock == null) {
            statusLabel.setText("La cerradura no se ha inicializado");
            return;
        }

        try {
            smartLock.lock();
            updateView();
            statusLabel.setText("Cerradura bloqueada correctamente");
        } catch (Exception e) {
            statusLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void onAddPinClick() {
        if (smartLock == null) {
            statusLabel.setText("La cerradura no se ha inicializado");
            return;
        }

        try {
            String user = userField.getText();
            String pin = newPinField.getText();

            smartLock.addPin(user, pin);
            updateView();
            statusLabel.setText("PIN agregado para el usuario: " + user);
        } catch (Exception e) {
            statusLabel.setText(e.getMessage());
        }
    }

    private void updateView() {
        if (smartLock == null) return;

        stateLabel.setText("Estado: " + smartLock.getStateName());
        attemptsLabel.setText("Intentos fallidos: " + smartLock.getFailedAttempts() + " / " + smartLock.getMaxFailedAttempts());
    }
}