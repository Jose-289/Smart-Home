package edu.smarthome.pattern.behavior.command;

import java.util.ArrayList;
import java.util.List;

public class MacroCommand implements Command {

    private final String description;
    private final List<Command> commands;
    private final List<Command> executed = new ArrayList<>();

    public MacroCommand(String description, List<Command> commands) {
        this.description = description;
        this.commands = List.copyOf(commands);
    }

    @Override public void execute() {
        executed.clear();
        for (Command c : commands) {
            c.execute();
            executed.add(c);
        }
    }

    @Override public void undo() {
        for (int i = executed.size() - 1; i >= 0; i--) {
            Command c = executed.get(i);
            if (c.isUndoable()) c.undo();
        }
    }

    // Se puede deshacer si al menos una de sus acciones es reversible
    @Override public boolean isUndoable() { return commands.stream().anyMatch(Command::isUndoable); }
    @Override public String description() { return description; }
    public boolean isEmpty() { return commands.isEmpty(); }
}