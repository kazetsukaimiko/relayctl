package com.notsafenotcensored.relayctl.endpoint;

import com.notsafenotcensored.relayctl.GPIORelayController;
import com.notsafenotcensored.relayctl.config.RelayState;
import com.notsafenotcensored.relayctl.relay.Controller;
import com.notsafenotcensored.relayctl.relay.Relay;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Path(RelayControlEndpoint.RELAYCTL_ROOT)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RelayControlEndpointImpl implements RelayControlEndpoint {

    @Inject
    private GPIORelayController controller;

    @GET
    public List<Relay> getStatus() {
        System.out.println(controller);
        return controller.getRelays().stream().sorted().collect(Collectors.toList());
    }

    @GET
    @Path("/id/{"+RelayControlEndpoint.ID_PARAM+"}")
    public Relay getRelayById(@PathParam(RelayControlEndpoint.ID_PARAM) String relayId) {
        return controller.getRelayById(relayId);
    }

    @GET
    @Path("/id/{"+RelayControlEndpoint.ID_PARAM+"}/{"+RelayControlEndpoint.STATE_PARAM+"}")
    public List<Relay> setRelayById(@PathParam(RelayControlEndpoint.ID_PARAM) String relayId, @PathParam(RelayControlEndpoint.STATE_PARAM) boolean state) {
        getRelayById(relayId).setState(state);
        return getStatus();
    }

    @GET
    @Path("/name/{"+RelayControlEndpoint.NAME_PARAM+"}")
    public List<Relay> getRelaysByName(@PathParam(RelayControlEndpoint.NAME_PARAM) String relayName) {
        return controller.getRelays().stream().sorted().filter(relay -> Objects.equals(relayName, relay.getName())).collect(Collectors.toList());
    }

    @GET
    @Path("/name/{"+RelayControlEndpoint.NAME_PARAM+"}/{"+RelayControlEndpoint.STATE_PARAM+"}")
    public List<Relay> setRelaysByName(@PathParam(RelayControlEndpoint.NAME_PARAM) String relayName, @PathParam(RelayControlEndpoint.STATE_PARAM) boolean state) {
        return getRelaysByName(relayName).stream().peek(relay -> relay.setState(state)).collect(Collectors.toList());
    }
}
