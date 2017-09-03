package com.notsafenotcensored.relayctl.relay;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Rule {
    private Relay relay;
    private RuleState state;
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

    public RuleState getState() {
        return state;
    }

    public void setState(RuleState state) {
        this.state = state;
    }

    public Rule state(RuleState ruleState) {
        this.state = ruleState; return this;
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

    public boolean enforce(Relay relay, RuleState state) {
        switch (state) {
            case FORCE_ON:
                return relay.on();
            case FORCE_OFF:
                return relay.off();
            case ON:
                return relay.getState();
            default:
                return !relay.getState();
        }
    }

    public boolean honor(Relay from) {
        if (getRelay() == null || getState() == null || isCyclic(new HashSet<>(Arrays.asList(from)))) {
            return true; // Cannot make rules this way.
        }
        return enforce(relay, getState());
    }

    public boolean isCyclic(Set<Relay> aboveMe) {
        if (aboveMe.contains(getRelay())) {
            return true;
        }
        Set<Relay> meAndBefore = new HashSet<>(aboveMe);
        meAndBefore.add(getRelay());
        for (Rule rule : getRelay().getRuleList()) {
            if (rule.isCyclic(meAndBefore)) {
                return true;
            }
        }
        return false;
    }
}
