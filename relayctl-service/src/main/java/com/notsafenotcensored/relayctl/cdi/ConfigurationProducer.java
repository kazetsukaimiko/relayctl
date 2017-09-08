package com.notsafenotcensored.relayctl.cdi;

import com.notsafenotcensored.relayctl.config.Configuration;
import com.notsafenotcensored.relayctl.config.DefaultConfiguration;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

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

    private static Logger logger = Logger.getLogger(ConfigurationProducer.class.getName());

    @Inject
    private ObjectMapper mapper;

    private static Path configDirectory = Paths.get(System.getProperty("user.dir"), "/.config/relayctl");
    private static Path configFile = Paths.get(configDirectory.toAbsolutePath().toString(), "config.json");

    @Produces @Default
    public static Configuration getConfiguration() {
        try {
            return loadConfiguration();
        } catch (IOException ioe) {
            logger.log(Level.WARNING, "Couldn't load configuration, defaulting: ", ioe);
            return new DefaultConfiguration();
        }
    }

    public static Configuration loadConfiguration() throws IOException {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationConfig.Feature.INDENT_OUTPUT);
        Files.createDirectories(configDirectory);
        if (Files.isRegularFile(configFile)) {
            return mapper.readValue(configFile.toFile(), Configuration.class);
        } else {
            mapper.writeValue(configFile.toFile(), new DefaultConfiguration());
            return loadConfiguration();
        }
    }
}
