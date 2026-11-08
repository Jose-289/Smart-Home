package edu.smarthome.pattern.behavior.strategy;

public class ComfortStrategy implements ClimateStrategy{

    @Override
    public String name (){
        return "COMFORT";
    }

    @Override
    public double adjustSetpoint(double requested){
        return Math.max(16, Math.min(30, requested));
    }

    @Override
    public double ratePerTick(){
        return 0.3; // rápido
    }

}
