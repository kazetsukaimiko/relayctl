package com.notsafenotcensored.relayctl.cdi;

import com.notsafenotcensored.relayctl.config.Configuration;
import com.notsafenotcensored.relayctl.relay.provider.FauxRelayProvider;
import com.notsafenotcensored.relayctl.relay.provider.GPIORelayProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

@ApplicationScoped
public class RelayProviderProducer {

    @Inject
    private Configuration configuration;

    private GPIORelayProvider gpioRelayProvider = null;

    @Produces @Default
    public GPIORelayProvider rpiRelayProvider(InjectionPoint injectionPoint) {
        if (gpioRelayProvider == null) {
            gpioRelayProvider = new GPIORelayProvider();
            gpioRelayProvider.load(configuration);
        }
        return gpioRelayProvider;
    }

    @Produces
    public FauxRelayProvider fauxRelayProvider(InjectionPoint injectionPoint) {
        return new FauxRelayProvider();
    }
}
