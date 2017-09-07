package com.notsafenotcensored.relayctl;


import com.notsafenotcensored.relayctl.relay.RelayController;
import com.pi4j.io.gpio.*;
import io.undertow.Undertow;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import org.jboss.resteasy.cdi.CdiInjectorFactory;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

import java.util.Scanner;

public class RelayctlMain {

    public static String getBindAddress() {
        return "0.0.0.0";
    }
    public static int getPort() {
        return 8080;
    }
    public static void main(String[] args) throws InterruptedException {
        test();
    }

    public static void test() throws InterruptedException {
        System.out.println("Making rc.");
        RelayController rc = new RelayController();

        System.out.println("Setting relays");
        for (GpioPinDigitalOutput gpioPDO : rc.getRelays()) {
            System.out.println("Setting gpio "+gpioPDO.getName());
            gpioPDO.setState(PinState.LOW);
            promptEnterKey();
        }

        System.out.println("Setting relays");
        for (GpioPinDigitalOutput gpioPDO : rc.getRelays()) {
            System.out.println("Setting gpio "+gpioPDO.getName());
            gpioPDO.setState(PinState.HIGH);
            promptEnterKey();
        }
        rc.shutdown();
    }

    public static void deploy() {
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

    public static void promptEnterKey(){
        System.out.println("Press \"ENTER\" to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

}