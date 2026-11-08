// SetClimateStrategyCommand.java
package edu.smarthome.pattern.behavior.command;

import edu.smarthome.model.Thermostat;
import edu.smarthome.pattern.behavior.strategy.ClimateStrategy;

public class SetClimateStrategyCommand implements Command {
    private final Thermostat thermostat;
    private final ClimateStrategy newStrategy;
    private ClimateStrategy previous;

    public SetClimateStrategyCommand(Thermostat thermostat, ClimateStrategy newStrategy) {
        this.thermostat = thermostat;
        this.newStrategy = newStrategy;
    }

    @Override public void execute() { previous = thermostat.getStrategy(); thermostat.setStrategy(newStrategy); }
    @Override public void undo()    { thermostat.setStrategy(previous); }
    @Override public String description() { return "Modo de clima " + newStrategy.name(); }
}