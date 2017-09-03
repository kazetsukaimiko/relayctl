package com.notsafenotcensored.relayctl;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.ws.rs.Path;

public class RelayService {
    private final Node node;

    @Produces
    @Default
    public Node getNode() {
        System.out.println("Inject!");
        return node;
    }

    public RelayService(Node node) {
        this.node = node;
    }
}