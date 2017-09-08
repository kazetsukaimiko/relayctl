package com.notsafenotcensored.relayctl;

import com.notsafenotcensored.relayctl.relay.Relay;
import com.notsafenotcensored.relayctl.relay.RelayController;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import org.junit.Test;

public class RelayTest {


    @Test
    public void testNode() throws InterruptedException {
        RelayController rc = new RelayController();
        for (Relay relay : rc.getRelays()) {
            relay.on();
            Thread.sleep(500);
        }
        rc.shutdown();
    }



}
