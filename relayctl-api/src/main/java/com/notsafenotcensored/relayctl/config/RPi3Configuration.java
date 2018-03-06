package com.notsafenotcensored.relayctl.config;

import com.notsafenotcensored.relayctl.relay.provider.GPIORelayProvider;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RPi3Configuration extends Configuration {

    public RPi3Configuration() {
        setListenPort(7272);
        setBindAddress("0.0.0.0");
        setRelays(generateDefaults());
    }

    private List<RelayConfig> generateDefaults() {
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
