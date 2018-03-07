package com.notsafenotcensored.relayctl.config;

import com.notsafenotcensored.relayctl.control.Control;
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

    public static final String DELIM = "\n#####################################";
    private static Path configDirectory = Paths.get(System.getProperty("user.home"), "/.config/relayctl");
    private static Path configFile = Paths.get(configDirectory.toAbsolutePath().toString(), "config.json");

    private int listenPort = 7272;
    private String bindAddress = "127.0.0.1";
    private List<RelayConfig> relays = new ArrayList<>();
    private List<Control> controls = new ArrayList<>();

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

    public List<Control> getControls() {
        return controls;
    }

    public void setControls(List<Control> controls) {
        this.controls = controls;
    }

    public static Configuration loadConfiguration() throws IOException {
        Files.createDirectories(configDirectory);
        return loadConfiguration(configFile);
    }

    public static Configuration loadConfiguration(Path configFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper()
                .enable(SerializationConfig.Feature.INDENT_OUTPUT)
                .disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);

        if (Files.isRegularFile(configFile)) {
            return mapper.readValue(configFile.toFile(), Configuration.class);
        } else {
            mapper.writeValue(configFile.toFile(), new RPi3Configuration());
            return loadConfiguration(configFile);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nConfiguration");
        sb.append(DELIM);
        sb.append("\nbindAddress: " + getBindAddress());
        sb.append("\nbindPort: " + getListenPort());
        sb.append("\n\nRelays: ");
        sb.append(DELIM);
        getRelays()
                .forEach(relayConfig -> sb.append(relayConfig.toString()));
        sb.append(DELIM);
        sb.append("\n\nControls: ");
        sb.append(DELIM);
        getControls()
                .forEach(control -> sb.append(control.toString()));
        sb.append(DELIM);
        return sb.toString();
    }
}
