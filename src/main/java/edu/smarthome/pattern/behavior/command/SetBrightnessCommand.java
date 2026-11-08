// SetBrightnessCommand.java
package edu.smarthome.pattern.behavior.command;

import edu.smarthome.model.Light;

public class SetBrightnessCommand implements Command {
    private final Light light;
    private final int newValue;
    private int previous;

    public SetBrightnessCommand(Light light, int newValue) {
        this.light = light;
        this.newValue = newValue;
    }

    @Override public void execute() { previous = light.getBrightness(); light.setBrightness(newValue); }
    @Override public void undo()    { light.setBrightness(previous); }
    @Override public String description() {
        return "Brillo de " + light.getName() + " a " + newValue + "%";
    }
}