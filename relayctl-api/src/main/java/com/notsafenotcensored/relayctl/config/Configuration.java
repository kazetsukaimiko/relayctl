package com.notsafenotcensored.relayctl.config;

import com.notsafenotcensored.relayctl.relay.Relay;

import java.util.ArrayList;
import java.util.List;

public class Configuration {
    private int listenPort = 7272;
    private List<Relay> relays = new ArrayList<>();

    public int getListenPort() {
        return listenPort;
    }

    public void setListenPort(int listenPort) {
        this.listenPort = listenPort;
    }

    public List<Relay> getRelays() {
        return relays;
    }

    public void setRelays(List<Relay> relays) {
        this.relays = relays;
    }
}
