package com.notsafenotcensored.relayctl.endpoint;

import com.notsafenotcensored.relayctl.QueryResult;
import com.notsafenotcensored.relayctl.relay.Controller;
import com.notsafenotcensored.relayctl.relay.RelayState;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.stream.Collectors;

@Path(RelayServiceEndpoint.ROOT)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RelayServiceEndpointImpl implements RelayServiceEndpoint {

    @Inject
    private Controller controller;

    @Override
    public QueryResult getStatus() {
        return new QueryResult(
                controller.getControls(),
                controller
                    .getRelays()
                    .stream()
                    .map(RelayState::new)
                    .sorted()
                    .peek(System.out::println)
                    .collect(Collectors.toList())
        );
    }

    @Override
    public QueryResult setRelayById(int relayId, boolean state) {
        controller
                .getRelayById(relayId)
                .ifPresent(relay -> relay.setState(state));
        return getStatus();
    }

    @Override
    public QueryResult setRelaysByName(String relayName, boolean state) {
        controller
                .getRelays()
                .stream()
                .sorted()
                .forEach(relay -> relay.setState(state));
        return getStatus();
    }

    @Override
    public QueryResult setControlByName(String controlName, String state) {
        controller.setControlState(controlName, state);
        return getStatus();
    }
}
