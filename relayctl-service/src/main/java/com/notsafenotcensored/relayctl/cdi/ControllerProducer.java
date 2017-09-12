package com.notsafenotcensored.relayctl.cdi;

import com.notsafenotcensored.relayctl.GPIORelayController;
import com.notsafenotcensored.relayctl.config.Configuration;
import com.notsafenotcensored.relayctl.relay.Controller;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@ApplicationScoped
public class ControllerProducer {
    @Inject
    private Configuration config;

    private GPIORelayController gpioRelayController;

    @Produces @Default
    public GPIORelayController getGpioRelayController() {
        System.out.println("Resolving Injection Point");
        if (gpioRelayController == null) {
            gpioRelayController = new GPIORelayController(config);
        }
        System.out.println("COntroller: "+gpioRelayController);
        return gpioRelayController;
    }

}
