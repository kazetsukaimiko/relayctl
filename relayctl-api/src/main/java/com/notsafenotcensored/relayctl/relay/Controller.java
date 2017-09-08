package com.notsafenotcensored.relayctl.relay;

import com.notsafenotcensored.relayctl.config.RelayState;

import java.util.List;

public interface Controller {

    List<Relay> getRelays();

    Relay getRelayById(int id);

    boolean getState(Relay relay);

    List<Relay> off(Relay relay);

    List<Relay> on(Relay relay);

    boolean isExecutable(Relay relay);

    void shutdown();
}
