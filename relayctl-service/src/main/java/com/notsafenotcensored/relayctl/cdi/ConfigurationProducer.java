package com.notsafenotcensored.relayctl.cdi;

import com.notsafenotcensored.relayctl.config.Configuration;
import com.notsafenotcensored.relayctl.config.DefaultConfiguration;
import org.codehaus.jackson.map.ObjectMapper;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigurationProducer {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Inject
    private ObjectMapper mapper;

    private Path configDirectory = Paths.get("~/.config/relayctl");
    private Path configFile = Paths.get(configDirectory.toAbsolutePath().toString(), "config.json");

    @Produces @Default
    public Configuration getConfiguration() {
        try {
            return loadConfiguration();
        } catch (IOException ioe) {
            logger.log(Level.WARNING, "Couldn't load configuration, defaulting: ", ioe);
            return new DefaultConfiguration();
        }
    }

    public Configuration loadConfiguration() throws IOException {
        Files.createDirectories(configDirectory);
        if (Files.isRegularFile(configFile)) {
            return mapper.readValue(configFile.toFile(), Configuration.class);
        } else {
            mapper.writeValue(configFile.toFile(), new DefaultConfiguration());
            return loadConfiguration();
        }
    }
}
