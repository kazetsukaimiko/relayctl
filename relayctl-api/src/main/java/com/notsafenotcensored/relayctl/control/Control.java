package com.notsafenotcensored.relayctl.control;

import com.notsafenotcensored.relayctl.config.Configuration;
import com.notsafenotcensored.relayctl.relay.RelayState;

import java.util.*;

// TODO : Perhaps Control should be a superset of relay in the future.
public class Control {
    private String name;
    private Map<String, List<RelayState>> states = new LinkedHashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, List<RelayState>> getStates() {
        return states;
    }

    public void setStates(Map<String, List<RelayState>> states) {
        this.states = states;
    }

    public Control name(String name) {
        setName(name); return this;
    }

    public Control state(String name, List<RelayState> stateList) {
        if (getStates() == null) {
            setStates(new HashMap<>());
        }
        getStates().put(name, stateList);
        return this;
    }
    public Control states(Map<String, List<RelayState>> states) {
        setStates(states); return this;
    }
    public Control validate() {
        if (getStates() == null) {
            setStates(new HashMap<>());
        }
        if (getStates().size() < 2) {
            throw new IllegalArgumentException("Control must have at least two states");
        }
        // Clone for concurrent
        Map<String, List<RelayState>> allStates = new HashMap<>(getStates());
        getStates()
                .forEach((stateName, stateList) -> {
                    allStates.forEach((comparisonName, comparisonStates) -> {
                        if (Objects.equals(stateName, comparisonName)) {
                            return;
                        }
                        if (stateList.containsAll(comparisonStates)) {
                            throw new IllegalArgumentException("Control states must be mutually exclusive: " +stateName + " vs" + comparisonName);
                        }
                    });
                });
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\nControl:");
        sb.append(Configuration.DELIM);
        sb.append("\nName: "); sb.append(getName());
        sb.append(Configuration.DELIM);
        return sb.toString();
    }
}
