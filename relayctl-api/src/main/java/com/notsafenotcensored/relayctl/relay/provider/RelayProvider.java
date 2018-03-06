package com.notsafenotcensored.relayctl.relay.provider;

import com.notsafenotcensored.relayctl.config.RelayConfig;
import com.notsafenotcensored.relayctl.relay.Relay;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class RelayProvider {
    public abstract Relay getRelay(RelayConfig relayConfig);

    public final boolean handles(RelayConfig relayConfig) {
        return Objects.equals(getClass().getSimpleName().toUpperCase(), relayConfig.getSource().toUpperCase());
    }
}
