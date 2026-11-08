package edu.smarthome.pattern.behavior.state;

import edu.smarthome.model.SmartLock;

public interface LockState {
    void lock(SmartLock lock);
    void unlock(SmartLock lock, String pin);
    String name();

    default boolean allowsPinManagement(){
        return false;
    }
}
