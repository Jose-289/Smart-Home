// UnlockDoorCommand.java
package edu.smarthome.pattern.behavior.command;

import edu.smarthome.model.SmartLock;

public class UnlockDoorCommand implements Command {
    private final SmartLock lock;
    private final String pin;

    public UnlockDoorCommand(SmartLock lock, String pin) {
        this.lock = lock;
        this.pin = pin;
    }

    @Override public void execute() { lock.unlock(pin); }
    @Override public void undo() { throw new UnsupportedOperationException("Abrir no se puede deshacer"); }
    @Override public boolean isUndoable() { return false; }
    @Override public String description() { return "Abrir " + lock.getName(); } // sin el PIN
}