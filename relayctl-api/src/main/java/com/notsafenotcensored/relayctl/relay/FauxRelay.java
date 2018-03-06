package com.notsafenotcensored.relayctl.relay;

import com.notsafenotcensored.relayctl.config.RelayConfig;
import com.notsafenotcensored.relayctl.relay.provider.FauxRelayProvider;

import java.util.logging.Logger;

public class FauxRelay extends Relay {

    private static Logger LOGGER = Logger.getLogger(FauxRelay.class.getName());
    private boolean state = false;
    private final RelayConfig relayConfig;

    public FauxRelay(RelayConfig relayConfig) {
        this.relayConfig = relayConfig;
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
        return FauxRelayProvider.class.getSimpleName();
    }

    @Override
    public boolean getState() {
        return state;
    }

    @Override
    public boolean setState(boolean state) {
        LOGGER.info("Setting state: " + (state ? "ON" : "OFF"));
        this.state = state; return state;
    }
}
