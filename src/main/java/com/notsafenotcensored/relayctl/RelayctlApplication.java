package com.notsafenotcensored.relayctl;

import org.reflections.Reflections;

import javax.inject.Singleton;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import java.lang.reflect.Modifier;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class RelayctlApplication extends Application {
    private static final Reflections reflections = new Reflections(RelayctlMain.class.getPackage().getName());
    private final Set<Class<?>> classes;
    private final Set<Object> singletons;

    public RelayctlApplication() {
        classes = reflections.getTypesAnnotatedWith(Path.class).stream()
                .filter(klazz -> !klazz.isInterface())
                .filter(klazz -> !Modifier.isAbstract(klazz.getModifiers()))
                .collect(Collectors.toSet());

        singletons = reflections.getTypesAnnotatedWith(Singleton.class).stream()
                .filter(singletonClass -> !Objects.equals(RelayService.class, singletonClass))
                .map(singletonClass -> {
                    try {
                        return singletonClass.newInstance();
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }


}
