package com.notsafenotcensored.relayctl.cdi;

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

    private Controller controller;

    @Produces @Default
    public Controller getController() {
        System.out.println("Resolving Injection Point");
        if (controller == null) {
            controller = new Controller(config);
        }
        System.out.println("COntroller: "+controller);
        return controller;
    }

}
