package edu.smarthome.pattern.behavior.command;

public interface Command {
    void execute();
    void undo();
    String description();

    /** Los comandos que no se pueden deshacer no entran al historial. */
    default boolean isUndoable() { return true; }
}
