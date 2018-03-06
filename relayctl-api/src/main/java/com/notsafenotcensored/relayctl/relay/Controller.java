package com.notsafenotcensored.relayctl.relay;

import com.notsafenotcensored.relayctl.config.Configuration;
import com.notsafenotcensored.relayctl.config.RelayConfig;
import com.notsafenotcensored.relayctl.relay.provider.RelayProvider;

import org.reflections.Reflections;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public final class Controller {
    private static Logger LOGGER = Logger.getLogger(Controller.class.getName());

    private final Set<Relay> relays;

    private final Configuration configuration;
    public Controller(Configuration configuration) {
        this.configuration = configuration;
        this.relays = populate();
    }

    private Set<RelayProvider> getProviders() {
        Reflections reflections = new Reflections("com.notsafenotcensored");
        Set<Class<? extends RelayProvider>> providerClasses = reflections.getSubTypesOf(RelayProvider.class);
        return providerClasses.stream()
                .map(providerClass -> {
                    try {
                        return providerClass.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        LOGGER.log(Level.FINEST, "Could not instantiate provider: ", e);
                        return null;
                    }
                })
                .filter(RelayProvider.class::isInstance)
                .map(RelayProvider.class::cast)
                .collect(Collectors.toSet());

    }

    private Set<Relay> populate() {
        Set<RelayProvider> providers = getProviders();
        return configuration
                .getRelays()
                .stream()
                .map(relayConfig -> ofProviders(relayConfig, providers))
                .flatMap(Optional::stream)
                .collect(Collectors.toSet());
    }

    private Optional<Relay> ofProviders(RelayConfig relayConfig, Set<RelayProvider> providers) {
        return providers
                .stream()
                .filter(relayProvider -> relayProvider.handles(relayConfig))
                .map(relayProvider -> relayProvider.getRelay(relayConfig))
                .findFirst();
    }

    public Set<Relay> getRelays() {
        return relays;
    }

    public Optional<Relay> getRelayById(int relayId) {
        return getRelays()
                .stream()
                .filter(relay -> Objects.equals(relay.getId(), relayId))
                .findFirst();
    }

    /*
    public boolean getState(Relay relay) {
        return relay.getState();
    }

    public Set<Relay> off(Relay relay) {
        relay.off(); return getRelays();
    }

    public Set<Relay> on(Relay relay) {
        relay.on(); return getRelays();
    }
    */
}