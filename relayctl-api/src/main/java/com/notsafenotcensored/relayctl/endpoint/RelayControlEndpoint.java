package com.notsafenotcensored.relayctl.endpoint;

import com.notsafenotcensored.relayctl.config.RelayState;
import com.notsafenotcensored.relayctl.relay.Relay;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path(RelayControlEndpoint.RELAYCTL_ROOT)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface RelayControlEndpoint {
    String RELAYCTL_ROOT = "/relay";
    String ID_PARAM = "id";
    String NAME_PARAM = "name";
    String STATE_PARAM = "state";

    @GET
    public List<RelayState> getStatus();

    @GET
    @Path("/id/{"+RelayControlEndpoint.ID_PARAM+")")
    public RelayState getRelayById(@PathParam(RelayControlEndpoint.ID_PARAM) int relayId);

    @GET
    @Path("/id/{"+RelayControlEndpoint.ID_PARAM+")/{"+RelayControlEndpoint.STATE_PARAM+"}")
    public List<RelayState> setRelayStateById(@PathParam(RelayControlEndpoint.ID_PARAM) int relayId, @PathParam(RelayControlEndpoint.STATE_PARAM) boolean state);

    @GET
    @Path("/name/{"+RelayControlEndpoint.NAME_PARAM+")")
    public List<RelayState> getRelaysByName(@PathParam(RelayControlEndpoint.NAME_PARAM) String relayName);

    @GET
    @Path("/name/{"+RelayControlEndpoint.NAME_PARAM+")/{"+RelayControlEndpoint.STATE_PARAM+"}")
    public List<RelayState> setRelayStatesName(@PathParam(RelayControlEndpoint.NAME_PARAM) String relayName, @PathParam(RelayControlEndpoint.STATE_PARAM) boolean state);


}
