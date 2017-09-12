package com.notsafenotcensored.relayctl.relay.provider;

import com.notsafenotcensored.relayctl.config.Configuration;
import com.notsafenotcensored.relayctl.config.RelayConfig;
import com.notsafenotcensored.relayctl.relay.Relay;
import com.pi4j.io.gpio.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GPIORelayProvider extends RelayProvider {

    private static final GPIORelayProvider INSTANCE = new GPIORelayProvider();
    private GpioController controller = null;
    private Set<Relay> relays = new HashSet<>();

    private GPIORelayProvider() {

    }

    public GPIORelayProvider get() {
        return this;
    }

    public static GPIORelayProvider load(List<RelayConfig> relayConfigs) {
        INSTANCE.loadConfigs(relayConfigs); return INSTANCE;
    }

    private GPIORelayProvider loadConfigs(List<RelayConfig> relayConfigs) {
        try {
            close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        relays = new HashSet<>();
        controller = GpioFactory.getInstance();

        relayConfigs.stream().map(this::addRelay).forEach(relays::add);

        return this;
    }


    private Relay addRelay(final RelayConfig relayConfig) {
        final GpioPinDigitalOutput backingPin = makeGpioPin(relayConfig.getId(), relayConfig.getName());
        return new Relay() {
            @Override
            public String getId() {
                return String.valueOf(relayConfig.getId());
            }

            @Override
            public String getName() {
                return relayConfig.getName();
            }

            @Override
            public boolean getState() {
                return backingPin.isState(PinState.LOW);
            }

            @Override
            public boolean setState(boolean state) {
                backingPin.setState(state? PinState.LOW : PinState.HIGH); return getState();
            }
        };
    }

    private GpioPinDigitalOutput makeGpioPin(int pinAddress, String name) {
        GpioPinDigitalOutput gpioPinDigitalOutput = controller.provisionDigitalOutputPin(RaspiPin.getPinByAddress(pinAddress), name, PinState.HIGH);
        gpioPinDigitalOutput.setShutdownOptions(true, PinState.HIGH);
        return gpioPinDigitalOutput;
    }

    @Override
    public Set<Relay> getRelays() {
        return relays;
    }

    @Override
    public void close() throws Exception {
        if (controller != null && !controller.isShutdown()) {
            controller.shutdown();
            controller = null;
        }
    }
}
