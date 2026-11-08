package edu.smarthome.model;

import edu.smarthome.pattern.behavior.observer.DeviceObserver;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
public abstract class Device {

    private final String id;
    private final String name;
    private final List <DeviceObserver> observers = new ArrayList<>();

    protected Device(String id, String name){
        this.id = id;
        this.name = name;
    }

    public void addObserver(DeviceObserver o){
        observers.add(o);
    }

    public void removeObserver(DeviceObserver o){
        observers.remove(o);
    }
    protected void notifyObservers(String type, String msg){
        var event = new DeviceEvent(id, type,msg);
        observers.forEach(o-> o.onDeviceEvent(event));
    }

    public String getId (){
        return id;
    }
    public String getName (){
        return name;
    }
}
