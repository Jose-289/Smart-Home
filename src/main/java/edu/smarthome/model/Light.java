package edu.smarthome.model;

public class Light extends Device {

    private boolean on = false;
    private int brightness = 100;

    public Light(String id, String name) {
        super(id, name);
    }

    public void setOn(boolean on) {
        this.on = on;
        notifyObservers(on ? "LIGHT_ON" : "LIGHT_OFF", getName() + (on ? " encendida" : " apagada"));
    }

    public void setBrightness(int brightness) {
        if (brightness < 0 || brightness > 100)
            throw new IllegalArgumentException("El brillo debe estar entre 0 y 100");
        this.brightness = brightness;
        notifyObservers("BRIGHTNESS_CHANGED", getName() + " al " + brightness + "%");
    }

    public boolean isOn()       { return on; }
    public int getBrightness()  { return brightness; }
}
