// pattern/behavior/state/LockedState.java
package edu.smarthome.pattern.behavior.state;

import edu.smarthome.model.SmartLock;

public class LockedState implements LockState {
    @Override public void lock(SmartLock l) { /* ya está bloqueada */ }

    @Override public void unlock(SmartLock l, String pin) {
        if (l.checkPin(pin)) {
            l.resetFailedAttempts();
            l.setState(new UnlockedState());
            l.emit("UNLOCKED", "Cerradura abierta");
        } else {
            l.registerFailedAttempt();
            if (l.getFailedAttempts() >= 3) {
                l.setState(new BlockedState());
                l.emit("BLOCKED", "Demasiados intentos fallidos");
            } else {
                l.emit("WRONG_PIN", "PIN incorrecto");
            }
        }
    }

    @Override public String name() { return "LOCKED"; }
}