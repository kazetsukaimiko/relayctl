package com.notsafenotcensored.relayctl.endpoint;

import com.notsafenotcensored.relayctl.control.Control;
import com.notsafenotcensored.relayctl.control.ControlState;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path(ControlEndpoint.ROOT)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ControlEndpoint {
    String ROOT = "/control";
    String NAME_PARAM = "name";
    String STATE_PARAM = "state";

    @GET
    List<ControlState> getStatus();

    @GET
    @Path("/name/{"+ ControlEndpoint.NAME_PARAM+"}")
    List<ControlState> getControlByName(@PathParam(ControlEndpoint.NAME_PARAM) String controlName);

    @GET
    @Path("/name/{"+ ControlEndpoint.NAME_PARAM+"}/{"+ ControlEndpoint.STATE_PARAM+"}")
    List<ControlState> setControlByName(@PathParam(ControlEndpoint.NAME_PARAM) String controlName, @PathParam(ControlEndpoint.STATE_PARAM) String state);


}
