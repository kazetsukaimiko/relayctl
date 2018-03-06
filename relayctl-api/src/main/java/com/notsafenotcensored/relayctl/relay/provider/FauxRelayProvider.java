package com.notsafenotcensored.relayctl.relay.provider;

import com.notsafenotcensored.relayctl.config.RelayConfig;
import com.notsafenotcensored.relayctl.relay.FauxRelay;
import com.notsafenotcensored.relayctl.relay.Relay;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FauxRelayProvider extends RelayProvider implements AutoCloseable {

    private final Set<Relay> relays;

    public FauxRelayProvider(int count) {
        relays = IntStream.range(0, count)
                .mapToObj(i -> new RelayConfig(
                        i,
                        "RELAY_"+String.format("%03d", i),
                        getClass().getSimpleName(),
                        new ArrayList<>()))
                .map(FauxRelay::new)
                .collect(Collectors.toSet());
    }

    public FauxRelayProvider() {
        this(16);
    }

    public Set<Relay> getRelays() {
        return relays;
    }

    @Override
    public void close() throws Exception {
        relays
                .forEach(Relay::off);
    }

    @Override
    public Relay getRelay(RelayConfig relayConfig) {
        return relays
                .stream()
                .filter(relay -> Objects.equals(relay.getId(), relayConfig.getId()))
                .findFirst()
                .orElse(null);
    }
}
