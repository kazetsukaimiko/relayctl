package com.notsafenotcensored.relayctl.relay;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Rule {
    private Relay relay;
    private RelayState relayState;
    private long delay = 0;

    public Relay getRelay() {
        return relay;
    }

    public void setRelay(Relay relay) {
        this.relay = relay;
    }

    public Rule relay(Relay relay) {
        this.relay = relay; return this;
    }

    public RelayState getRelayState() {
        return relayState;
    }

    public void setRelayState(RelayState relayState) {
        this.relayState = relayState;
    }

    public Rule relayState(RelayState relayState) {
        this.relayState = relayState; return this;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public Rule delay(long delay) {
        this.delay = delay; return this;
    }

    public boolean honor(Relay from) {
        if (getRelay() == null || getRelayState() == null || isCyclic(from, new HashSet<>())) {
            return true; // Cannot make rules this way.
        }
        boolean active = getRelay().getState();
        if (getRelayState() == RelayState.ON || getRelayState() == RelayState.FORCE_ON) {
            if (active)
        }
        if (getRelayState() == RelayState.OFF || getRelayState() == RelayState.FORCE_OFF) {

        }
    }

    private boolean isCyclic(Relay from, Set<Integer> ruleIds) {
        if (getRelay() == null) {
            return false;
        }
        for (Rule rule : getRelay().getRuleList()) {

        }

    }
}
