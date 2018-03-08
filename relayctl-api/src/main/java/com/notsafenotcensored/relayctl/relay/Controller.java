package com.notsafenotcensored.relayctl.relay;

import com.notsafenotcensored.relayctl.config.Configuration;
import com.notsafenotcensored.relayctl.config.RelayConfig;
import com.notsafenotcensored.relayctl.control.Control;
import com.notsafenotcensored.relayctl.control.ControlState;
import com.notsafenotcensored.relayctl.relay.provider.RelayProvider;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Controller {
    private static Logger LOGGER = Logger.getLogger(Controller.class.getName());

    private final Set<Relay> relays;
    private final Set<RelayProvider> providers;

    private final Configuration configuration;
    public Controller(Configuration configuration, Set<RelayProvider> providers) {
        this.configuration = configuration;
        this.providers = providers;
        this.relays = populate();
    }

    private Set<Relay> populate() {
        System.out.println("POPULATE");
        return configuration
                .getRelays()
                .stream()
                .flatMap(this::ofProviders)
                .peek(relay -> System.out.println("Relay Configured: \n" + new RelayState(relay).toString()))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Stream<Relay> ofProviders(RelayConfig relayConfig) {
        return providers
                .stream()
                .peek(relayProvider -> System.out.println("Considering: " + relayProvider))
                .filter(relayProvider -> relayProvider.handles(relayConfig))
                .peek(relayProvider -> System.out.println("Found provider: " + relayProvider.getClass().getSimpleName() + " for Relay " + relayConfig.getName()))
                .map(relayProvider -> relayProvider.getRelay(relayConfig))
                .flatMap(Optional::stream);
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

    public List<ControlState> getControls() {
        return configuration.getControls()
                .stream()
                .map(this::getControlState)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }

    public Optional<ControlState> getControlState(Control control) {
        List<RelayState> allStates = getRelays()
                .stream()
                .map(RelayState::new)
                .collect(Collectors.toList());

        return control.getStates()
                .entrySet()
                .stream()
                .filter(e -> {
                    List<RelayState> states = e.getValue();
                    return allStates.containsAll(states);
                })
                .map(e -> new ControlState(control, e.getKey()))
                .findFirst();
    }

    public List<ControlState> setControlState(String controlName, String desiredState) {
        configuration.getControls()
                .stream()
                .filter(control -> control.getName().equals(controlName))
                .forEach(control -> {
                    Optional.of(control)
                            .map(Control::getStates)
                            .map(map -> map.get(desiredState))
                            .ifPresent(stateList -> {
                                stateList
                                        .forEach(relayState -> {
                                            setRelayState(relayState);
                                        });
                            });
                });
        return getControls();
    }

    private List<RelayState> setRelayState(RelayState relayState) {
        relays
                .stream()
                .filter(relay -> Objects.equals(relay.getId(), relayState.getId()))
                .forEach(relay -> relay.setState(relayState.getState()));
        return relays
                .stream()
                .sorted()
                .map(RelayState::new)
                .collect(Collectors.toList());
    }

}