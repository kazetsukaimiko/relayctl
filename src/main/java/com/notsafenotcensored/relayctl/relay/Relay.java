package com.notsafenotcensored.relayctl.relay;

import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Relay {
    private int id = -1;
    private String name = "";
    private List<Rule> ruleList = new ArrayList<>();
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

    public List<Rule> getRuleList() {
        return ruleList;
    }

    public void setRuleList(List<Rule> ruleList) {
        this.ruleList = ruleList;
    }

    public Relay ruleList(List<Rule> ruleList) {
        this.ruleList = ruleList; return this;
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

    public boolean on() {
        if (backingPin != null) {
            for (Rule rule : ruleList) {
                if (!rule.honor()) {
                    return false;
                }
            }
            backingPin.setState(PinState.HIGH);
        } return getState();
    }

    public boolean off() {
        if (backingPin != null) {
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
