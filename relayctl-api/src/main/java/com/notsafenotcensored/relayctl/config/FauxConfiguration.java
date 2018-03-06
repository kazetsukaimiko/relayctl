package com.notsafenotcensored.relayctl.config;

import com.notsafenotcensored.relayctl.relay.provider.FauxRelayProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FauxConfiguration extends Configuration {

    public FauxConfiguration() {
        this(10081, "127.0.0.1", 16);
    }

    public FauxConfiguration(int listenPort, String bindAddress, int count) {
        setListenPort(listenPort);
        setBindAddress(bindAddress);
        setRelays(IntStream.range(0, count)
                .mapToObj(i -> new RelayConfig()
                        .id(i)
                        .name(String.format("FAUX_RELAY_%0"+String.valueOf(count).length()+"d",i))
                        .source(FauxRelayProvider.class.getSimpleName()))
                .collect(Collectors.toList()));
    }
}
