package com.notsafenotcensored.relayctl.cdi;

import com.notsafenotcensored.relayctl.config.Configuration;
import com.notsafenotcensored.relayctl.config.RPi3Configuration;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class ConfigurationProducer {

    private static Logger logger = Logger.getLogger(ConfigurationProducer.class.getName());


    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        if (Configuration.getLocal() != null) {
            System.out.println("Loading local.");
            configuration = Configuration.getLocal();
            return;
        }

        System.out.println("Loading from FS.");
        try {
            configuration = Configuration.loadConfiguration();
        } catch (IOException e) {
            e.printStackTrace();
            logger.log(Level.WARNING, "Couldn't load configuration, defaulting: ", e);
            configuration = new RPi3Configuration();
        }
    }

    private Configuration configuration;

    @Produces @Default
    public Configuration getConfiguration() {
        System.out.println("Reading..");
        return configuration;
    }

}
