// LockDoorCommand.java
package edu.smarthome.pattern.behavior.command;

import edu.smarthome.model.SmartLock;

public class LockDoorCommand implements Command {
    private final SmartLock lock;

    public LockDoorCommand(SmartLock lock) { this.lock = lock; }

    @Override public void execute() { lock.lock(); }
    @Override public void undo() { throw new UnsupportedOperationException("Cerrar no se puede deshacer"); }
    @Override public boolean isUndoable() { return false; }
    @Override public String description() { return "Cerrar " + lock.getName(); }
}