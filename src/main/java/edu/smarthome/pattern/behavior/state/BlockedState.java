package edu.smarthome.pattern.behavior.state;

import edu.smarthome.model.SmartLock;

public class BlockedState implements LockState {

    @Override
    public void lock(SmartLock l) {
        l.emit("BLOCKED", "Cerradura bloqueada: use el PIN maestro");
    }

    @Override
    public void unlock(SmartLock l, String pin) {
        if (l.checkMasterPin(pin)) {
            l.resetFailedAttempts();
            l.setState(new LockedState());
            l.emit("RESET", "Bloqueo levantado con PIN maestro");
        } else {
            l.emit("BLOCKED", "Cerradura bloqueada: PIN rechazado");
        }
    }

    @Override
    public String name() { return "BLOCKED"; }
}