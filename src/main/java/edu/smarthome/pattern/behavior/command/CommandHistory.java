package edu.smarthome.pattern.behavior.command;

import java.util.ArrayDeque;
import java.util.Deque;

public class CommandHistory {

    private final Deque<Command> undoStack = new ArrayDeque<>();
    private final Deque<Command> redoStack = new ArrayDeque<>();

    public void execute(Command command) {
        command.execute();               // si lanza excepción, no se registra
        redoStack.clear();               // una acción nueva invalida el "rehacer"
        if (command.isUndoable()) undoStack.push(command);
    }

    public void undo() {
        if (!canUndo()) return;
        Command c = undoStack.pop();
        c.undo();
        redoStack.push(c);
    }

    public void redo() {
        if (!canRedo()) return;
        Command c = redoStack.pop();
        c.execute();
        undoStack.push(c);
    }

    public boolean canUndo() { return !undoStack.isEmpty(); }
    public boolean canRedo() { return !redoStack.isEmpty(); }

    public String nextUndoDescription() { return canUndo() ? undoStack.peek().description() : ""; }
    public String nextRedoDescription() { return canRedo() ? redoStack.peek().description() : ""; }
}