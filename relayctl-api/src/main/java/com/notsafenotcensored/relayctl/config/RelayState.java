package com.notsafenotcensored.relayctl.config;

import com.notsafenotcensored.relayctl.relay.Rule;

import java.util.List;

public class RelayState extends RelayConfig {
    private boolean state = false;

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public RelayState state(boolean state) {
        this.state = state; return this;
    }

    public RelayState(RelayConfig relayConfig, boolean state) {
        super(relayConfig);
        this.state = state;
    }

    public RelayState(RelayConfig relayConfig) {
        super(relayConfig);
    }

    public RelayState(int id, String name, List<Rule> rules, boolean state) {
        super(id, name, rules);
        this.state = state;
    }

    public RelayState(int id, String name, List<Rule> rules) {
        super(id, name, rules);
    }

    public RelayState() {
    }
}
