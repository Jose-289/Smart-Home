package edu.smarthome.model;

import edu.smarthome.pattern.behavior.strategy.ClimateStrategy;

import java.util.Objects;

public class Thermostat extends Device {

    private double targetTemperature;
    private double currentTemperature;
    private ClimateStrategy strategy;
    private boolean reachedNotified = false;

    public Thermostat(String id, String name, double initialTemperature, ClimateStrategy strategy) {
        super(id, name);
        this.currentTemperature = initialTemperature;
        this.targetTemperature = initialTemperature;
        this.strategy = Objects.requireNonNull(strategy);
    }

    public void setTargetTemperature(double target) {
        if (target < 10 || target > 35)
            throw new IllegalArgumentException("La temperatura debe estar entre 10 y 35 °C");
        this.targetTemperature = target;
        this.reachedNotified = false;
        notifyObservers("TARGET_CHANGED",
                String.format("Objetivo %.1f °C (efectivo en modo %s: %.1f °C)",
                        target, strategy.name(), getEffectiveSetpoint()));
    }

    public void setStrategy(ClimateStrategy newStrategy) {
        this.strategy = Objects.requireNonNull(newStrategy);
        this.reachedNotified = false;
        notifyObservers("STRATEGY_CHANGED", "Modo " + newStrategy.name());
    }

    /** Se llama periódicamente (un Timeline de JavaFX) para simular el clima. */
    public void tick() {
        double setpoint = getEffectiveSetpoint();
        double diff = setpoint - currentTemperature;

        if (Math.abs(diff) < 0.05) {
            if (!reachedNotified) {
                currentTemperature = setpoint;
                reachedNotified = true;
                notifyObservers("TEMP_REACHED", String.format("Temperatura alcanzada: %.1f °C", setpoint));
            }
            return;
        }
        double step = Math.min(Math.abs(diff), strategy.ratePerTick());
        currentTemperature += Math.signum(diff) * step;
    }

    public double getEffectiveSetpoint()   { return strategy.adjustSetpoint(targetTemperature); }
    public double getTargetTemperature()   { return targetTemperature; }
    public double getCurrentTemperature()  { return currentTemperature; }
    public ClimateStrategy getStrategy()   { return strategy; }
}
