package com.notsafenotcensored.relayctl.config;

import com.notsafenotcensored.relayctl.control.Control;
import com.notsafenotcensored.relayctl.relay.RelayState;
import com.notsafenotcensored.relayctl.relay.provider.GPIORelayProvider;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RPi3Configuration extends Configuration {

    public RPi3Configuration() {
        setListenPort(7272);
        setBindAddress("0.0.0.0");
        setRelays(generateDefaultRelays());
        setControls(generateDefaultControls());
    }

    private RelayState getRelayAs(int id, boolean state) {
        return getRelays()
                .stream()
                .filter(relayConfig -> Objects.equals(id, relayConfig.getId()))
                .findFirst()
                .map(relayConfig -> new RelayState(relayConfig, state))
                .orElse(null);

    }

    private List<Control> generateDefaultControls() {
        return Arrays.asList(
                new Control()
                    .name("Rear Blower")
                    .state(
                        "Off",
                        Arrays.asList(
                                getRelayAs(1, false),
                                getRelayAs(2, false),
                                getRelayAs(3, false)
                        )
                )
                    .state(
                        "Low",
                        Arrays.asList(
                                getRelayAs(1, true),
                                getRelayAs(2, false),
                                getRelayAs(3, false)
                        )
                    )
                    .state(
                        "Medium",
                        Arrays.asList(
                                getRelayAs(1, true),
                                getRelayAs(2, true),
                                getRelayAs(3, false)
                        )
                    )
                    .state(
                        "High",
                        Arrays.asList(
                                getRelayAs(1, true),
                                getRelayAs(2, true),
                                getRelayAs(3, true)
                        )
                )
        );
    }

    private List<RelayConfig> generateDefaultRelays() {
        int[] i = {1};
        return pins.stream().map(pin -> {
            String relayName = "RELAY_"+i[0]++;
            return new RelayConfig()
                    .id(pin.getAddress())
                    .name(relayName)
                    .source(GPIORelayProvider.class.getSimpleName());
        }).collect(Collectors.toList());
    }

    private List<Pin> pins = Arrays.asList(
            RaspiPin.GPIO_03, // 01
            RaspiPin.GPIO_02, // 02
            RaspiPin.GPIO_00, // 03
            RaspiPin.GPIO_01, // 04
            RaspiPin.GPIO_04, // 05
            RaspiPin.GPIO_07, // 06
            RaspiPin.GPIO_05, // 07
            RaspiPin.GPIO_06, // 08
            RaspiPin.GPIO_29, // 09
            RaspiPin.GPIO_28, // 10
            RaspiPin.GPIO_27, // 11
            RaspiPin.GPIO_26, // 12
            RaspiPin.GPIO_21, // 13
            RaspiPin.GPIO_22, // 14
            RaspiPin.GPIO_23, // 15
            RaspiPin.GPIO_24  // 16
    );
}
