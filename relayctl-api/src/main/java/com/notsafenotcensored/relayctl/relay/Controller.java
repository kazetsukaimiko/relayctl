package com.notsafenotcensored.relayctl.relay;

import com.notsafenotcensored.relayctl.relay.provider.RelayProvider;

import java.util.Set;

public abstract class Controller implements AutoCloseable {

    public abstract RelayProvider getProvider();

    public Set<Relay> getRelays() {
        return getProvider().getRelays();
    }

    public Relay getRelayById(String id) {
        return getProvider().getRelayById(id);
    }

    public boolean getState(Relay relay) {
        return relay.getState();
    }

    public Set<Relay> off(Relay relay) {
        relay.off(); return getRelays();
    }

    public Set<Relay> on(Relay relay) {
        relay.on(); return getRelays();
    }
}
