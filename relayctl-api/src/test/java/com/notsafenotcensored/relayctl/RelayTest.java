package com.notsafenotcensored.relayctl;

import com.notsafenotcensored.relayctl.config.Configuration;
import com.notsafenotcensored.relayctl.config.FauxConfiguration;
import com.notsafenotcensored.relayctl.relay.Controller;
import com.notsafenotcensored.relayctl.relay.Relay;
import com.notsafenotcensored.relayctl.relay.provider.FauxRelayProvider;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;

public class RelayTest {

    @Test
    public void testNode() throws InterruptedException {
        Controller controller = new Controller(new FauxConfiguration(), new HashSet<>(Collections.singleton(new FauxRelayProvider().load(new FauxConfiguration()))));
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

    @Test
    public void writeConfiguration() throws IOException {
        Configuration.loadConfiguration(Paths.get("/tmp/relayconfig.json"));
    }



}
