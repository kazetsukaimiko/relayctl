package com.notsafenotcensored.relayctl.relay.provider;

import com.notsafenotcensored.relayctl.config.Configuration;
import com.notsafenotcensored.relayctl.config.RelayConfig;
import com.notsafenotcensored.relayctl.relay.Relay;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class RelayProvider {
    public abstract Optional<Relay> getRelay(RelayConfig relayConfig);

    public final boolean handles(RelayConfig relayConfig) {
        boolean iHandle = Objects.equals(getClass().getSimpleName().toUpperCase(), relayConfig.getSource().toUpperCase());
        System.out.println(getClass().getSimpleName() + (iHandle? " handles":" doesn't handle ")+"this relay:"+relayConfig.toString());
        return iHandle;
    }

    public abstract RelayProvider load(Configuration configuration);
}
