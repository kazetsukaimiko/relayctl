package com.notsafenotcensored.relayctl.relay;

import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Relay {
    private int id = -1;
    private String name = "";
    private List<Rule> onRules = new ArrayList<>();
    private List<Rule> offRules = new ArrayList<>();
    private transient GpioPinDigitalOutput backingPin;

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

    public List<Rule> getOnRules() {
        return onRules;
    }

    public void setOnRules(List<Rule> onRules) {
        this.onRules = onRules;
    }
    public Relay onRules(List<Rule> onRules) {
        this.onRules = onRules; return this;
    }

    public List<Rule> getOffRules() {
        return offRules;
    }

    public void setOffRules(List<Rule> offRules) {
        this.offRules = offRules;
    }

    public Relay offRules(List<Rule> offRules) {
        this.offRules = offRules; return this;
    }

    public GpioPinDigitalOutput getBackingPin() {
        return backingPin;
    }

    public void setBackingPin(GpioPinDigitalOutput backingPin) {
        this.backingPin = backingPin;
    }

    public Relay backingPin(GpioPinDigitalOutput backingPin) {
        this.backingPin = backingPin; return this;
    }

    public boolean getState() {
        return (backingPin != null)? backingPin.isState(PinState.HIGH) : false;
    }

    // Returns TRUE if on.
    public boolean on() {
        if (backingPin != null) {
            for (Rule rule : onRules) {
                if (!rule.honor(this)) {
                    return false;
                }
            }
            backingPin.setState(PinState.HIGH);
        } return getState();
    }

    // Returns TRUE if off.
    public boolean off() {
        if (backingPin != null) {
            for (Rule rule : offRules) {
                if (!rule.honor(this)) {
                    return false;
                }
            }
            backingPin.setState(PinState.LOW);
        }   return !getState();
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
