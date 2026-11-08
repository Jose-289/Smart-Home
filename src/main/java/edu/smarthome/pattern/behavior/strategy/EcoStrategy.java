package edu.smarthome.pattern.behavior.strategy;

public class EcoStrategy implements ClimateStrategy{

    @Override
    public String name(){
        return "ECO";
    }

    @Override
    public double adjustSetpoint(double requested){
        return Math.max(20,Math.min(24, requested));
    }
    @Override
    public double ratePerTick(){
        return 0.1; // lento
    }
}
