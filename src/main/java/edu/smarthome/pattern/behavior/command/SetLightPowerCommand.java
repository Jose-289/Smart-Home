// SetLightPowerCommand.java
package edu.smarthome.pattern.behavior.command;

import edu.smarthome.model.Light;

public class SetLightPowerCommand implements Command {
    private final Light light;
    private final boolean newValue;
    private boolean previous;

    public SetLightPowerCommand(Light light, boolean newValue) {
        this.light = light;
        this.newValue = newValue;
    }

    @Override public void execute() { previous = light.isOn(); light.setOn(newValue); }
    @Override public void undo()    { light.setOn(previous); }
    @Override public String description() {
        return (newValue ? "Encender " : "Apagar ") + light.getName();
    }
}