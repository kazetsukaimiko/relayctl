package com.notsafenotcensored.relayctl.relay.provider;

import com.notsafenotcensored.relayctl.config.Configuration;
import com.notsafenotcensored.relayctl.config.RelayConfig;
import com.notsafenotcensored.relayctl.relay.GPIORelay;
import com.notsafenotcensored.relayctl.relay.Relay;
import com.pi4j.io.gpio.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class GPIORelayProvider extends RelayProvider implements AutoCloseable {

    private static final GPIORelayProvider INSTANCE = new GPIORelayProvider(Configuration.getLocal());
    private GpioController controller = null;
    private Set<Relay> relays = new HashSet<>();
    private Configuration configuration;

    private GPIORelayProvider(Configuration configuration) {
        this.configuration = configuration;
        try {
            close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        relays = new HashSet<>();
        controller = GpioFactory.getInstance();

        configuration
                .getRelays()
                .stream()
                .map(this::addRelay)
                .forEach(relays::add);

    }

    private Relay addRelay(final RelayConfig relayConfig) {
        final GpioPinDigitalOutput backingPin = makeGpioPin(relayConfig.getId(), relayConfig.getName());
        return new GPIORelay(relayConfig, backingPin);
    }

    private GpioPinDigitalOutput makeGpioPin(int pinAddress, String name) {
        GpioPinDigitalOutput gpioPinDigitalOutput = controller.provisionDigitalOutputPin(RaspiPin.getPinByAddress(pinAddress), name, PinState.HIGH);
        gpioPinDigitalOutput.setShutdownOptions(true, PinState.HIGH);
        return gpioPinDigitalOutput;
    }


    @Override
    public void close() throws Exception {
        if (controller != null && !controller.isShutdown()) {
            controller.shutdown();
            controller = null;
        }
    }

    @Override
    public Relay getRelay(RelayConfig relayConfig) {
        return relays.stream()
                .filter(r -> Objects.equals(r.getId(), String.valueOf(relayConfig.getId())))
                .findFirst()
                .orElse(null);
    }
}
