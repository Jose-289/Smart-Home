package edu.smarthome.pattern.behavior.state;

import edu.smarthome.model.SmartLock;

public class UnlockedState implements LockState {

    @Override
    public void lock(SmartLock l) {
        l.setState(new LockedState());
        l.emit("LOCKED", "Cerradura cerrada");
    }

    @Override
    public void unlock(SmartLock l, String pin) {
        l.emit("INFO", "La cerradura ya está abierta");
    }

    @Override
    public String name() { return "UNLOCKED"; }

    @Override
    public boolean allowsPinManagement() { return true; }
}