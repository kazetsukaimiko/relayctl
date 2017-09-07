package com.notsafenotcensored.relayctl.relay;

import java.util.List;

public interface Controller {
    Relay getRelayById(int id);

    boolean getState(Relay relay);

    List<Relay> off(Relay relay);

    List<Relay> on(Relay relay);

    boolean isExecutable(Relay relay);
}
