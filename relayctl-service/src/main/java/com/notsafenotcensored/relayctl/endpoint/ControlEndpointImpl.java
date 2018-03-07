package com.notsafenotcensored.relayctl.endpoint;

import com.notsafenotcensored.relayctl.control.Control;
import com.notsafenotcensored.relayctl.control.ControlState;
import com.notsafenotcensored.relayctl.relay.Controller;
import com.notsafenotcensored.relayctl.relay.Relay;
import com.notsafenotcensored.relayctl.relay.RelayState;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Path(ControlEndpoint.ROOT)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ControlEndpointImpl implements ControlEndpoint {

    @Inject
    private Controller controller;

    @Override
    public List<ControlState> getStatus() {
        return controller.getControls();
    }

    @Override
    public List<ControlState> getControlByName(String controlName) {
        return controller
                .getControls()
                .stream()
                .filter(controlState -> Objects.equals(controlState.getName(), controlName))
                .collect(Collectors.toList());
    }

    @Override
    public List<ControlState> setControlByName(String controlName, String state) {
        return controller.setControlState(controlName, state);
    }
}
