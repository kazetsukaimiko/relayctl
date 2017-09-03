package com.notsafenotcensored.relayctl;


import io.undertow.Undertow;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import org.jboss.resteasy.cdi.CdiInjectorFactory;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

public class RelayctlMain {

    public static String getBindAddress() {
        return "0.0.0.0";
    }
    public static int getPort() {
        return 8080;
    }
    public static void main(String[] args) {
        UndertowJaxrsServer server = new UndertowJaxrsServer();
        Undertow.Builder builder = Undertow.builder()
                .addHttpListener(getPort(), getBindAddress());
        server.start(builder);

        ResteasyDeployment deployment = new ResteasyDeployment();
        deployment.setApplication(new RelayctlApplication());
        deployment.setInjectorFactoryClass(CdiInjectorFactory.class.getName());

        DeploymentInfo di = server.undertowDeployment(deployment, "/");

        di.setClassLoader(RelayctlMain.class.getClassLoader())
                .setContextPath("/")
                .setDeploymentName("Relayctl")
                .addListeners(Servlets.listener(org.jboss.weld.environment.servlet.Listener.class));

        server.deploy(di);
    }

}
