package com.notsafenotcensored.relayctl.relay;

import com.notsafenotcensored.relayctl.config.Configuration;
import com.notsafenotcensored.relayctl.config.RelayConfig;
import com.notsafenotcensored.relayctl.config.RelayState;
import com.pi4j.io.gpio.*;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@ApplicationScoped
public class RelayController implements Controller {
    private Logger logger = Logger.getLogger(getClass().getName());
    private transient GpioController gpio = GpioFactory.getInstance();

    private List<Relay> relays = new ArrayList<>();

    @Inject
    private Configuration config;

    @PostConstruct
    public RelayController init() {
        logger.info("PostConstruct");
        load(config);
        return this;
    }

    public RelayController config(Configuration config) {
        this.config = config; return this;
    }

    private void load(Configuration config) {
        config.getRelays().forEach(this::addRelay);
    }

    private void addRelay(final RelayConfig relayConfig) {
        Relay relay = new Relay(relayConfig);
        logger.info("Adding relay: "+relay.getName());
        if (relay != null) {
            relay.setController(this);
            int index = relays.indexOf(relay);
            if (index > -1) {
                logger.info("Updating relay "+relay.getName());
                relays.get(index).getRelayConfig().setName(relay.getName());
                relays.get(index).getRelayConfig().setRules(relay.getRules());
            } else {
                logger.info("Initializing relay "+relay.getName());
                relay.setGpioPin(makeGpioPin(relay));
                relays.add(relay);
            }
        }
    }

    private GpioPinDigitalOutput makeGpioPin(Relay relay) {
        GpioPinDigitalOutput gpioPinDigitalOutput = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(relay.getId()), relay.getName(), PinState.HIGH);
        gpioPinDigitalOutput.setShutdownOptions(true, PinState.HIGH);
        return gpioPinDigitalOutput;
    }

    public void shutdown() {
        gpio.shutdown();
    }

    @Override
    public List<Relay> getRelays() {
        return relays.stream().collect(Collectors.toList());
    }

    public Relay getRelayById(int id) {
        return relays.stream().filter(relay -> relay.getId() == id).findFirst().get();
    }

    public boolean getState(Relay relay) {
        return relay.getGpioPin() != null && relay.getGpioPin().getState() == PinState.LOW;
    }

    public List<Relay> off(Relay relay) {
        if (isExecutable(relay)) {
            relay.getGpioPin().setState(PinState.HIGH);
        }   return getRelays();
    }

    public List<Relay> on(Relay relay) {
        if (isExecutable(relay)) {
            relay.getGpioPin().setState(PinState.LOW);
        }   return getRelays();
    }

    public boolean isExecutable(Relay relay) {
        return isExecutable(relay, new ArrayList<Relay>());
    }
    private boolean isExecutable(Relay level, List<Relay> aboveMe) {
        if (!aboveMe.contains(level)) {
            List<Relay> here = new ArrayList<Relay>(aboveMe);
            here.add(level);
            long failures = level.getRules().stream()
                    .map(rule -> getRelayById(rule.getThem()))
                    .map(relay -> isExecutable(relay, here))
                    .filter(executable -> !executable)
                    .count();
            return failures == 0L;
        }
        logger.warning("Cyclic failure: " + aboveMe.stream().map(Relay::getId).map(String::valueOf).collect(Collectors.joining(" -> ")));
        return false;
    }
}