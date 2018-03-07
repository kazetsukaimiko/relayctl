package com.notsafenotcensored.relayctl;

import com.notsafenotcensored.relayctl.config.Configuration;
import com.notsafenotcensored.relayctl.config.FauxConfiguration;
import com.notsafenotcensored.relayctl.endpoint.RelayControlEndpoint;
import com.notsafenotcensored.relayctl.relay.RelayState;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class RelayControlDaemonTest {

    private static final Configuration configuration = new FauxConfiguration();
    private static RelayControlDaemon controller;
    private static RelayControlEndpoint endpoint;
    @Before
    public void before() throws InterruptedException {
        controller = new RelayControlDaemon(configuration);
        controller.start();
        endpoint = (RelayControlEndpoint) controller;
        Thread.sleep(2500);
    }

    @After
    public void after() {
        controller.stop();
        controller = null;
    }


    @Test
    public void testConfigurationLoads() {
        List<RelayState> relayStateList = endpoint.getStatus();
        assertEquals(configuration.getRelays().size(), relayStateList.size());
    }

    @Test
    public void testRelayStateChange() {
        List<RelayState> relayStateList = endpoint.getStatus();
        relayStateList
                .stream()
                .findFirst()
                .ifPresent(relayState -> {
                    endpoint.setRelayById(relayState.getId(), !relayState.getState());
                    RelayState comparing = endpoint.getRelayById(relayState.getId());
                    assertEquals(relayState.getState(), comparing.getState());
                });
    }



}
