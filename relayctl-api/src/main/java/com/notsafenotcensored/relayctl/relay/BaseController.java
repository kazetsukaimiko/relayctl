package com.notsafenotcensored.relayctl.relay;

import com.notsafenotcensored.relayctl.config.Configuration;
import com.notsafenotcensored.relayctl.relay.provider.RelayProvider;
import com.pi4j.io.gpio.PinState;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public abstract class BaseController extends Controller {
    private Logger logger = Logger.getLogger(getClass().getName());

    protected RelayProvider relayProvider;

    @Override
    public RelayProvider getProvider() {
        return relayProvider;
    }

    public BaseController(RelayProvider relayProvider) {
        this.relayProvider = relayProvider;
    }

    @Override
    public Set<Relay> getRelays() {
        return relayProvider.getRelays();
    }

    @Override
    public void close() throws Exception {
        relayProvider.close();
    }
}