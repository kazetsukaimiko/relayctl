package com.notsafenotcensored.relayctl.endpoint;

import com.notsafenotcensored.relayctl.config.Configuration;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path(ConfigurationEndpoint.ROOT)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ConfigurationEndpoint {
    String ROOT = "/config";

    @GET
    Configuration getConfiguration();
}
