package com.notsafenotcensored.relayctl;

import com.notsafenotcensored.relayctl.config.FauxConfiguration;
import com.notsafenotcensored.relayctl.relay.Controller;
import com.notsafenotcensored.relayctl.relay.Relay;
import org.junit.Test;

public class RelayTest {

    @Test
    public void testNode() throws InterruptedException {
        Controller controller = new Controller(new FauxConfiguration());
        controller
                .getRelays()
                .stream()
                .peek(r -> {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                })
                .forEach(Relay::on);
    }



}
