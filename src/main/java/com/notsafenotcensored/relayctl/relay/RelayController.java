package com.notsafenotcensored.relayctl.relay;

import com.pi4j.io.gpio.*;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@ApplicationScoped
public class RelayController {
    private GpioController gpio = GpioFactory.getInstance();
    // Pin -> Relay Mapping
    // Default, get from config file.
    // ~/.config/relayctl/config.json
    private List<Pin> pins = Arrays.asList(
            RaspiPin.GPIO_00,
            RaspiPin.GPIO_01,
            RaspiPin.GPIO_03,
            RaspiPin.GPIO_04,
            RaspiPin.GPIO_05,
            RaspiPin.GPIO_06,
            RaspiPin.GPIO_21,
            RaspiPin.GPIO_22,
            RaspiPin.GPIO_23,
            RaspiPin.GPIO_24,
            RaspiPin.GPIO_25,
            RaspiPin.GPIO_26,
            RaspiPin.GPIO_27,
            RaspiPin.GPIO_28,
            RaspiPin.GPIO_29
    );

    List<GpioPinDigitalOutput> provision(List<Pin> pins) {
        final int[] i = {0};
        return pins.stream()
                .map(pin -> gpio.provisionDigitalOutputPin(pin, "RELAY_"+i[0]++, PinState.LOW))
                .collect(Collectors.toList());
    }
    private List<GpioPinDigitalOutput> provisioned = provision(pins);

    public Optional<Integer> findRelayId(RaspiPin raspiPin) {
        int relayId = pins.indexOf(raspiPin);
        if (relayId >= 0) {
            return Optional.of(relayId);
        }
        return Optional.empty();
    }
    public Optional<GpioPinDigitalOutput> findPin(RaspiPin raspiPin) {
        return findRelayId(raspiPin).map(provisioned::get);
    }
    

}