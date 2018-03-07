package com.notsafenotcensored.relayctl.cdi;

import com.notsafenotcensored.relayctl.config.Configuration;
import com.notsafenotcensored.relayctl.relay.Controller;
import com.notsafenotcensored.relayctl.relay.provider.GPIORelayProvider;
import com.notsafenotcensored.relayctl.relay.provider.RelayProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.util.Collections;
import java.util.HashSet;

@ApplicationScoped
public class ControllerProducer {
    @Inject
    private Configuration config;

    @Inject @Any
    private Instance<RelayProvider> relayProviders;

    @Inject
    private GPIORelayProvider gpioRelayProvider;

    private Controller controller;

    @Produces @Default
    public Controller getController() {
        if (controller == null) {
            /*
            Set<RelayProvider> relayProviderSet = StreamSupport.stream(relayProviders.spliterator(), false)
                    .peek(relayProvider -> relayProvider.load(config))
                    .collect(Collectors.toSet());
            */
            controller = new Controller(config, new HashSet<>(Collections.singleton(gpioRelayProvider)));
        }
        return controller;
    }

}
