package com.notsafenotcensored.relayctl.config;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Configuration {

    private static Path configDirectory = Paths.get(System.getProperty("user.dir"), "/.config/relayctl");
    private static Path configFile = Paths.get(configDirectory.toAbsolutePath().toString(), "config.json");


    private int listenPort = 7272;
    private String bindAddress = "127.0.0.1";
    private List<RelayConfig> relays = new ArrayList<>();

    private static final ThreadLocal<Configuration> local = new ThreadLocal<>();
    public static Configuration getLocal() {
        return local.get();
    }

    public static void setLocal(Configuration local) {
        Configuration.local.set(local);
    }

    public int getListenPort() {
        return listenPort;
    }

    public void setListenPort(int listenPort) {
        this.listenPort = listenPort;
    }

    public String getBindAddress() {
        return bindAddress;
    }

    public void setBindAddress(String bindAddress) {
        this.bindAddress = bindAddress;
    }

    public List<RelayConfig> getRelays() {
        return relays;
    }

    public void setRelays(List<RelayConfig> relays) {
        this.relays = relays;
    }

    public static Configuration loadConfiguration() throws IOException {
        ObjectMapper mapper = new ObjectMapper()
                .enable(SerializationConfig.Feature.INDENT_OUTPUT)
                .disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);

        Files.createDirectories(configDirectory);
        if (Files.isRegularFile(configFile)) {
            return mapper.readValue(configFile.toFile(), Configuration.class);
        } else {
            mapper.writeValue(configFile.toFile(), new RPi3Configuration());
            return loadConfiguration();
        }
    }
}
