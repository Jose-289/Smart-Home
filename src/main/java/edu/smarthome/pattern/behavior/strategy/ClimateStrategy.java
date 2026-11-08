package edu.smarthome.pattern.behavior.strategy;

public interface ClimateStrategy {
    String name();

    /**
     Ajusta la temperatura pedida por el usuario
     a lo que permite este metodo
     */
    double adjustSetpoint(double requested);

    /**
     Grados que sube o baja la temperatura
     */
    double ratePerTick();
}
