package com.notsafenotcensored.relayctl.relay.provider;

import com.notsafenotcensored.relayctl.relay.Relay;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class RelayProvider implements AutoCloseable {
    public abstract Set<Relay> getRelays();

    public Relay getRelayById(String id) {
        return getRelays().stream().filter(relay -> Objects.equals(id, relay.getId())).findFirst().get();
    }

    public Set<Relay> getRelaysByName(String name) {
        return getRelays().stream().filter(relay -> Objects.equals(name, relay.getName())).collect(Collectors.toSet());
    }
}
