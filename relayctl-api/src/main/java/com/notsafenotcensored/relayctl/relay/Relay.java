package com.notsafenotcensored.relayctl.relay;

import com.notsafenotcensored.relayctl.config.RelayConfig;
import com.notsafenotcensored.relayctl.config.RelayState;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Relay {
    private transient Controller controller;

    private transient GpioPinDigitalOutput gpioPin;

    private RelayConfig relayConfig;

    public Relay(RelayConfig relayConfig) {
        this.relayConfig = relayConfig;
    }

    public Relay() {
    }

    public RelayConfig getRelayConfig() {
        return relayConfig;
    }

    public void setRelayConfig(RelayConfig relayConfig) {
        this.relayConfig = relayConfig;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public int getId() {
        return relayConfig.getId();
    }
    public String getName() {
        return relayConfig.getName();
    }
    public List<Rule> getRules() {
        return relayConfig.getRules();
    }

    public RelayState toState() {
        return new RelayState(getRelayConfig(), getState());
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
    public List<Relay> on() {
        return controller.on(this);
    }


    // Returns TRUE if off.
    public List<Relay> off() {
        return controller.off(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Relay relay = (Relay) o;
        return getRelayConfig() == relay.getRelayConfig();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRelayConfig());
    }

}
