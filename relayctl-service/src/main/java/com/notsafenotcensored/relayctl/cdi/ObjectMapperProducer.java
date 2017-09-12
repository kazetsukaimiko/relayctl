package com.notsafenotcensored.relayctl.cdi;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class ObjectMapperProducer {
    private ObjectMapper objectMapper = null;

    @Produces @Default
    public ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper()
                    .enable(SerializationConfig.Feature.INDENT_OUTPUT)
                    .disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
        }   return objectMapper;
    }
}
