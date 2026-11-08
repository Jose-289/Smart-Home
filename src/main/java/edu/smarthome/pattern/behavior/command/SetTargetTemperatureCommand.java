// SetTargetTemperatureCommand.java
package edu.smarthome.pattern.behavior.command;

import edu.smarthome.model.Thermostat;

public class SetTargetTemperatureCommand implements Command {
    private final Thermostat thermostat;
    private final double newValue;
    private double previous;

    public SetTargetTemperatureCommand(Thermostat thermostat, double newValue) {
        this.thermostat = thermostat;
        this.newValue = newValue;
    }

    @Override public void execute() { previous = thermostat.getTargetTemperature(); thermostat.setTargetTemperature(newValue); }
    @Override public void undo()    { thermostat.setTargetTemperature(previous); }
    @Override public String description() {
        return String.format("Temperatura objetivo a %.1f °C", newValue);
    }
}