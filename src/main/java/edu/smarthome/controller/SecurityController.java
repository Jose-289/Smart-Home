package edu.smarthome.controller;

import edu.smarthome.model.SmartLock;
import edu.smarthome.service.SmartHomeFacade;

public class SecurityController {

    private final SmartHomeFacade facade = new SmartHomeFacade();

    private SmartLock smartLock;

    //Crea la cerradura utilizando el Facade y el patrón Factory.
    public void createSmartLock(String name, String masterPin, String firstUser, String firstPin){

        smartLock = facade.createSmartLock(name, masterPin, firstUser, firstPin);
    }

    //Desbloquea la casa utilizando el Mediator.
    public void  unlockHome(String pin){
        facade.unlockHome(pin);
    }

    //Bloquea la casa utilizando el Mediator.
    public void lockHome(){
        facade.lockHome();
    }

    public SmartLock getSmartLock(){
        return smartLock;
    }

    //Permite consultar el estado actual de la cerradura.
    public String getLockState(){
        if (smartLock == null){
            throw new IllegalStateException("La cerradura no ha sido creada");
        }

        return smartLock.getStateName();
    }
}
