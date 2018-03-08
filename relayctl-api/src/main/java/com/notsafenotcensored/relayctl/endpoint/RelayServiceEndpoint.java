package com.notsafenotcensored.relayctl.endpoint;


import com.notsafenotcensored.relayctl.QueryResult;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path(RelayServiceEndpoint.ROOT)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface RelayServiceEndpoint extends EndpointCommon {
    String ROOT = "/api";

    @GET
    QueryResult getStatus();

    @GET
    @Path("/relays/id/{"+EndpointCommon.ID_PARAM+"}/{"+EndpointCommon.STATE_PARAM+"}")
    QueryResult setRelayById(@PathParam(EndpointCommon.ID_PARAM) int relayId, @PathParam(EndpointCommon.STATE_PARAM) boolean state);

    @GET
    @Path("/relays/name/{"+EndpointCommon.NAME_PARAM+"}/{"+EndpointCommon.STATE_PARAM+"}")
    QueryResult setRelaysByName(@PathParam(EndpointCommon.NAME_PARAM) String relayName, @PathParam(EndpointCommon.STATE_PARAM) boolean state);

    @GET
    @Path("/controls/name/{"+ EndpointCommon.NAME_PARAM+"}/{"+ EndpointCommon.STATE_PARAM+"}")
    QueryResult setControlByName(@PathParam(EndpointCommon.NAME_PARAM) String controlName, @PathParam(EndpointCommon.STATE_PARAM) String state);

}
