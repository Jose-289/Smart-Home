package edu.smarthome.pattern.behavior.strategy;

public class NightStrategy implements ClimateStrategy{
    @Override
    public String name(){
        return "NIGHT";
    }

    @Override
    public double adjustSetpoint(double requested){
        return Math.max(17, Math.min(20, requested)),
    }

    @Override
    public double ratePerTick (){
        return 0.15;
    }
}
