package com.notsafenotcensored.relayctl.relay;

import com.notsafenotcensored.relayctl.config.RelayConfig;
import com.notsafenotcensored.relayctl.relay.provider.GPIORelayProvider;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;

public class GPIORelay extends Relay {

    private final RelayConfig relayConfig;
    private final GpioPinDigitalOutput backingPin;
    public GPIORelay(RelayConfig relayConfig, GpioPinDigitalOutput backingPin) {
        this.relayConfig = relayConfig;
        this.backingPin = backingPin;
    }

    @Override
    public int getId() {
        return relayConfig.getId();
    }

    @Override
    public String getName() {
        return relayConfig.getName();
    }

    @Override
    public String getSource() {
        return GPIORelayProvider.class.getSimpleName();
    }

    @Override
    public boolean getState() {
        return backingPin.isState(PinState.LOW);
    }

    @Override
    public boolean setState(boolean state) {
        backingPin.setState(state? PinState.LOW : PinState.HIGH); return getState();
    }
}
