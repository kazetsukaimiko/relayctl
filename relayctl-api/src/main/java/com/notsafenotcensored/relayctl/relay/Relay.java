package com.notsafenotcensored.relayctl.relay;

import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Relay {
    private int id = -1;
    private String name = "";

    
    private List<Rule> rules = new ArrayList<>();

    @JsonIgnore
    private transient Controller controller;

    @JsonIgnore
    private transient GpioPinDigitalOutput gpioPin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Relay id(int id) {
        this.id = id; return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Relay name(String name) {
        this.name = name; return this;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public boolean getState() {
        return controller.getState(this);
    }

    public GpioPinDigitalOutput getGpioPin() {
        return gpioPin;
    }

    public void setGpioPin(GpioPinDigitalOutput gpioPin) {
        this.gpioPin = gpioPin;
    }

    // Returns TRUE if on.
    public boolean on() {
        return controller.on(this);
    }


    // Returns TRUE if off.
    public boolean off() {
        return controller.off(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Relay relay = (Relay) o;
        return id == relay.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
