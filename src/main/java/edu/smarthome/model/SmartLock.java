package edu.smarthome.model;

import edu.smarthome.pattern.behavior.command.Command;
import edu.smarthome.pattern.behavior.command.LockDoorCommand;
import edu.smarthome.pattern.behavior.state.LockState;
import edu.smarthome.pattern.behavior.state.LockedState;

import java.util.*;

public class SmartLock extends Device {

    private static final int MAX_FAILED_ATTEMPTS = 3;

    private final Map<String, String> pins = new LinkedHashMap<>(); // usuario -> PIN
    private String masterPin;
    private LockState state = new LockedState();
    private int failedAttempts = 0;

    public SmartLock(String id, String name, String masterPin, String firstUser, String firstPin) {
        super(id, name);
        validatePin(masterPin);
        validatePin(firstPin);
        this.masterPin = masterPin;
        this.pins.put(firstUser, firstPin);
    }

    // ---------- Operaciones principales (delegan en el estado: patrón State) ----------
    public void lock()             { state.lock(this); }
    public void unlock(String pin) { state.unlock(this, pin); }

    // ---------- Gestión de PIN (solo si el estado actual lo permite) ----------
    public void addPin(String user, String pin) {
        requirePinManagement();
        validatePin(pin);
        if (pins.containsKey(user)) throw new IllegalArgumentException("El usuario ya tiene un PIN");
        if (pins.containsValue(pin) || masterPin.equals(pin)) throw new IllegalArgumentException("PIN no disponible");
        pins.put(user, pin);
        emit("PIN_ADDED", "PIN creado para " + user);
    }

    public void updatePin(String user, String newPin) {
        requirePinManagement();
        validatePin(newPin);
        if (!pins.containsKey(user)) throw new IllegalArgumentException("Usuario no encontrado");
        if (pins.containsValue(newPin) || masterPin.equals(newPin)) throw new IllegalArgumentException("PIN no disponible");
        pins.put(user, newPin);
        emit("PIN_UPDATED", "PIN actualizado para " + user);
    }

    public void removePin(String user) {
        requirePinManagement();
        if (!pins.containsKey(user)) throw new IllegalArgumentException("Usuario no encontrado");
        if (pins.size() == 1) throw new IllegalStateException("Debe existir al menos un PIN");
        pins.remove(user);
        emit("PIN_REMOVED", "PIN eliminado para " + user);
    }

    public void changeMasterPin(String currentMaster, String newMaster) {
        requirePinManagement();
        if (!checkMasterPin(currentMaster)) throw new IllegalArgumentException("PIN maestro actual incorrecto");
        validatePin(newMaster);
        if (pins.containsValue(newMaster)) throw new IllegalArgumentException("PIN no disponible");
        this.masterPin = newMaster;
        emit("MASTER_PIN_CHANGED", "PIN maestro actualizado");
    }

    public Set<String> getUsers() {
        return Collections.unmodifiableSet(pins.keySet()); // nunca se exponen los PIN
    }

    // ---------- Métodos que usan los estados ----------
    public boolean checkPin(String pin)       { return pin != null && pins.containsValue(pin); }
    public boolean checkMasterPin(String pin) { return masterPin.equals(pin); }
    public void setState(LockState newState)  { this.state = newState; }
    public void registerFailedAttempt()       { failedAttempts++; }
    public void resetFailedAttempts()         { failedAttempts = 0; }
    public int getFailedAttempts()            { return failedAttempts; }
    public int getMaxFailedAttempts()         { return MAX_FAILED_ATTEMPTS; }
    public String getStateName()              { return state.name(); }

    public void emit(String type, String msg) { notifyObservers(type, msg); }

    // ---------- Internos ----------
    private void requirePinManagement() {
        if (!state.allowsPinManagement())
            throw new IllegalStateException("Abra la cerradura para gestionar los PIN");
    }

    private static void validatePin(String pin) {
        if (pin == null || !pin.matches("\\d{4,8}"))
            throw new IllegalArgumentException("El PIN debe tener entre 4 y 8 dígitos");
    }

    @Override
    public List<Command> lockCommands() {
        return List.of(new LockDoorCommand(this));
    }
}
