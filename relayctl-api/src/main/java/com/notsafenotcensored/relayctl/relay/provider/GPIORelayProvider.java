package com.notsafenotcensored.relayctl.relay.provider;

import com.notsafenotcensored.relayctl.config.Configuration;
import com.notsafenotcensored.relayctl.config.RelayConfig;
import com.notsafenotcensored.relayctl.relay.GPIORelay;
import com.notsafenotcensored.relayctl.relay.Relay;
import com.pi4j.io.gpio.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class GPIORelayProvider extends RelayProvider implements AutoCloseable {

    private final GpioController controller = GpioFactory.getInstance();
    private Set<Relay> relays = new HashSet<>();

    public GPIORelayProvider() {
    }

    public RelayProvider load(Configuration configuration) {
        System.out.println("Loading GPIORelayProvider...");
        configuration
                .getRelays()
                .stream()
                .map(this::makeRelay)
                .forEach(relays::add);

        System.out.println("Found " + relays.size() + " relays.");
        return this;
    }

    private Relay makeRelay(final RelayConfig relayConfig) {
        final GpioPinDigitalOutput backingPin = makeGpioPin(relayConfig.getId(), relayConfig.getName());
        return new GPIORelay(relayConfig, backingPin);
    }

    private GpioPinDigitalOutput makeGpioPin(int pinAddress, String name) {
        System.out.println("Provisioning "+pinAddress+"...");
        GpioPinDigitalOutput gpioPinDigitalOutput = controller.provisionDigitalOutputPin(RaspiPin.getPinByAddress(pinAddress), name, PinState.HIGH);
        gpioPinDigitalOutput.setShutdownOptions(true, PinState.HIGH);
        return gpioPinDigitalOutput;
    }


    @Override
    public void close() throws Exception {
        if (controller != null && !controller.isShutdown()) {
            controller.shutdown();
        }
    }

    @Override
    public Optional<Relay> getRelay(RelayConfig relayConfig) {
        return relays.stream()
                .filter(r -> Objects.equals(r.getId(), relayConfig.getId()))
                .findFirst();
    }
}
