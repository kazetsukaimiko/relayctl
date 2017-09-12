package com.notsafenotcensored.relayctl;


import com.notsafenotcensored.relayctl.cdi.ConfigurationProducer;
import com.notsafenotcensored.relayctl.relay.BaseController;
import com.notsafenotcensored.relayctl.relay.Controller;
import com.notsafenotcensored.relayctl.relay.Relay;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import org.jboss.resteasy.cdi.CdiInjectorFactory;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.jboss.weld.resources.DefaultResourceLoader;

import java.util.Scanner;

import static io.undertow.Handlers.resource;

public class RelayctlMain {

    private static final String APP_URL_SUFFIX = "/";

    public static String getBindAddress() {
        return "0.0.0.0";
    }
    public static int getPort() {
        return 8080;
    }
    public static void main(String[] args) throws Exception {
        ClassPathResourceManager resourceManager = new ClassPathResourceManager(RelayctlMain.class.getClassLoader(), RelayctlMain.class.getPackage());
        ResourceHandler resourceHandler = resource(resourceManager).addWelcomeFiles("index.html").setDirectoryListingEnabled(true);

        ResteasyDeployment deployment = new ResteasyDeployment();
        deployment.setApplication(new RelayctlApplication());
        deployment.setInjectorFactoryClass(CdiInjectorFactory.class.getName());

        //UndertowJaxrsServer server = new UndertowJaxrsServer();
        //Undertow.Builder builder = Undertow.builder()
        Undertow server = Undertow.builder()
                .setHandler(Handlers.path()
                        .addPrefixPath("/static", createStaticResourceHandler())
                        .addPrefixPath("/", createRestApiHandler())

                )
                .addHttpListener(getPort(), getBindAddress())
                .build();
                server.start();

    }


    private static HttpHandler createStaticResourceHandler() {
        return new ResourceHandler(
                new ClassPathResourceManager(RelayctlMain.class.getClassLoader())
        ).addWelcomeFiles("index.html");
    }


    private static HttpHandler createRestApiHandler() throws Exception {
        final UndertowJaxrsServer server = new UndertowJaxrsServer();

        ResteasyDeployment deployment = new ResteasyDeployment();
        deployment.setApplication(new RelayctlApplication());
        deployment.setInjectorFactoryClass(CdiInjectorFactory.class.getName());

        DeploymentInfo di = server.undertowDeployment(deployment, "/rest")
                .setClassLoader(RelayctlMain.class.getClassLoader())
                .setContextPath("/")
                .setDeploymentName("Relayctl API")
                .addListener(Servlets.listener(org.jboss.weld.environment.servlet.Listener.class));
        DeploymentManager manager = Servlets.defaultContainer().addDeployment(di);
        manager.deploy();
        HttpHandler servletHandler = manager.start();
        return servletHandler;
    }

    /*
    public static void promptEnterKey(){
        System.out.println("Press \"ENTER\" to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }*/

}
