package com.notsafenotcensored.relayctl.endpoint;

import com.notsafenotcensored.relayctl.relay.Controller;
import com.notsafenotcensored.relayctl.relay.RelayState;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Path(RelayControlEndpoint.RELAYCTL_ROOT)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RelayControlEndpointImpl implements RelayControlEndpoint {

    @Inject
    private Controller controller;

    @GET
    public List<RelayState> getStatus() {
        System.out.println(controller);
        return controller
                .getRelays()
                .stream()
                .map(RelayState::new)
                .sorted()
                .collect(Collectors.toList());
    }

    @GET
    @Path("/id/{"+RelayControlEndpoint.ID_PARAM+"}")
    public RelayState getRelayById(@PathParam(RelayControlEndpoint.ID_PARAM) int relayId) {
        return controller
                .getRelayById(relayId)
                .map(RelayState::new)
                .orElse(null);
    }

    @GET
    @Path("/id/{"+RelayControlEndpoint.ID_PARAM+"}/{"+RelayControlEndpoint.STATE_PARAM+"}")
    public List<RelayState> setRelayById(@PathParam(RelayControlEndpoint.ID_PARAM) int relayId, @PathParam(RelayControlEndpoint.STATE_PARAM) boolean state) {
        getRelayById(relayId).setState(state);
        return getStatus();
    }

    @GET
    @Path("/name/{"+RelayControlEndpoint.NAME_PARAM+"}")
    public List<RelayState> getRelaysByName(@PathParam(RelayControlEndpoint.NAME_PARAM) String relayName) {
        return controller
                .getRelays()
                .stream()
                .filter(relay -> Objects.equals(relayName, relay.getName()))
                .map(RelayState::new)
                .sorted()
                .collect(Collectors.toList());
    }

    @GET
    @Path("/name/{"+RelayControlEndpoint.NAME_PARAM+"}/{"+RelayControlEndpoint.STATE_PARAM+"}")
    public List<RelayState> setRelaysByName(@PathParam(RelayControlEndpoint.NAME_PARAM) String relayName, @PathParam(RelayControlEndpoint.STATE_PARAM) boolean state) {
        return controller
                .getRelays()
                .stream()
                .sorted()
                .peek(relay -> relay.setState(state))
                .map(RelayState::new)
                .collect(Collectors.toList());
    }
}
