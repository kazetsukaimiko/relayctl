package com.notsafenotcensored.relayctl;

import com.notsafenotcensored.relayctl.config.Configuration;

public class RPi3DaemonMain {
    public static void main(String[] args) throws Exception {
        new RelayControlDaemon(Configuration.loadConfiguration()).start();
    }
}
