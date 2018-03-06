package com.notsafenotcensored.relayctl;

import com.notsafenotcensored.relayctl.config.Configuration;
import com.notsafenotcensored.relayctl.endpoint.RelayControlEndpoint;
import com.notsafenotcensored.relayctl.relay.Relay;
import com.notsafenotcensored.relayctl.relay.RelayState;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import org.jboss.resteasy.cdi.CdiInjectorFactory;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

import javax.servlet.ServletException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import java.util.List;

import static io.undertow.Handlers.resource;

public class RelayControlDaemon implements AutoCloseable, RelayControlEndpoint {

    private Thread thread;
    private Undertow server;
    private RelayControlEndpoint relayControlEndpoint;



    public RelayControlDaemon(Configuration configuration) {
        Configuration.setLocal(configuration);
    }

    public RelayControlDaemon() {

    }

    public Configuration getConfiguration() {
        return Configuration.getLocal();
    }


    public void initialize(Configuration configuration) {
        Configuration.setLocal(configuration);

        ClassPathResourceManager resourceManager = new ClassPathResourceManager(RPi3DaemonMain.class.getClassLoader(), RPi3DaemonMain.class.getPackage());
        ResourceHandler resourceHandler = resource(resourceManager)
                .addWelcomeFiles("index.html")
                .setDirectoryListingEnabled(true);

        ResteasyDeployment deployment = new ResteasyDeployment();
        deployment.setApplication(new RelayctlApplication());
        deployment.setInjectorFactoryClass(CdiInjectorFactory.class.getName());

        System.out.println("Booting up at : " + getConfiguration().getBindAddress() + ":" + getConfiguration().getListenPort());

        server = Undertow.builder()
                    .setHandler(Handlers.path()
                            .addPrefixPath("/static", createStaticResourceHandler())
                            .addPrefixPath("/", createRestApiHandler())

                    )
                    .addHttpListener(getConfiguration().getListenPort(), getConfiguration().getBindAddress())
                    .build();

            server.start();
    }

    public RelayControlDaemon start() {
        final Configuration configuration = getConfiguration();
        System.out.println("Booting up at : " + configuration.getBindAddress() + ":" + configuration.getListenPort());
        //thread = new Thread(() -> this.initialize(configuration));

        this.initialize(configuration);
        Client client = ClientBuilder.newClient();
        WebTarget target = client
                .target("http://"+getConfiguration().getBindAddress()+":"+getConfiguration().getListenPort()+"/rest");
        ResteasyWebTarget rtarget = (ResteasyWebTarget)target;
        relayControlEndpoint = rtarget.proxy(RelayControlEndpoint.class);

        return this;
    }


    public RelayControlDaemon block() throws InterruptedException {
        thread.wait();
        return this;
    }

    public RelayControlDaemon stop() {
        server.stop();
        return this;
    }

    private static HttpHandler createStaticResourceHandler() {
        return new ResourceHandler(
                new ClassPathResourceManager(RPi3DaemonMain.class.getClassLoader())
        ).addWelcomeFiles("index.html");
    }


    private static HttpHandler createRestApiHandler() {
        final UndertowJaxrsServer server = new UndertowJaxrsServer();

        ResteasyDeployment deployment = new ResteasyDeployment();
        deployment.setApplication(new RelayctlApplication());
        deployment.setInjectorFactoryClass(CdiInjectorFactory.class.getName());

        DeploymentInfo di = server.undertowDeployment(deployment, "/rest")
                .setClassLoader(RPi3DaemonMain.class.getClassLoader())
                .setContextPath("/")
                .setDeploymentName("Relayctl API")
                .addListener(Servlets.listener(org.jboss.weld.environment.servlet.Listener.class));
        DeploymentManager manager = Servlets.defaultContainer().addDeployment(di);
        manager.deploy();
        try {
            return manager.start();
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void close() throws Exception {
        this.stop();
    }

    @Override
    public List<RelayState> getStatus() {
        return relayControlEndpoint.getStatus();
    }

    @Override
    public RelayState getRelayById(int relayId) {
        return relayControlEndpoint.getRelayById(relayId);
    }

    @Override
    public List<RelayState> setRelayById(int relayId, boolean state) {
        return relayControlEndpoint.setRelayById(relayId, state);
    }

    @Override
    public List<RelayState> getRelaysByName(String relayName) {
        return relayControlEndpoint.getRelaysByName(relayName);
    }

    @Override
    public List<RelayState> setRelaysByName(String relayName, boolean state) {
        return setRelaysByName(relayName, state);
    }
}
