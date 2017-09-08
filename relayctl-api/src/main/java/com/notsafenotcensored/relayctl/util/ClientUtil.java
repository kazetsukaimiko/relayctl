package com.notsafenotcensored.relayctl.util;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class ClientUtil {

    public static <T> T getClient(Class<T> clientClass) {
        return getClient(clientClass, "localhost");
    }

    public static <T> T getClient(Class<T> clientClass, String hostname) {
        return getClient(clientClass, hostname, 7272);
    }

    public static <T> T getClient(Class<T> clientClass, String hostname, int port) {
        return getClient(clientClass, hostname, port, "");
    }
    public static <T> T getClient(Class<T> clientClass, String hostname, int port, String root) {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://"+hostname+":"+port+"/"+root);
        return target.proxy(clientClass);
    }
}