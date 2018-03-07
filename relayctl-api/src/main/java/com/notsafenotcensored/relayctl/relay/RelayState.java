package com.notsafenotcensored.relayctl.relay;

import com.notsafenotcensored.relayctl.config.RelayConfig;

import java.util.Objects;

public class RelayState implements Comparable<RelayState> {
    private int id;
    private String name;
    private String source;
    private boolean state;
    private String myType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getMyType() {
        return myType;
    }

    public void setMyType(String myType) {
        this.myType = myType;
    }

    public RelayState() {

    }

    public RelayState(Relay relay) {
        setId(relay.getId());
        setName(relay.getName());
        setSource(relay.getSource());
        setState(relay.getState());
        setMyType(relay.getMyType());
    }
    public RelayState(RelayConfig relayConfig, boolean state) {
        setId(relayConfig.getId());
        setName(relayConfig.getName());
        setSource(relayConfig.getSource());
        setMyType("Uhhhhhh");
        setState(state);
    }

    @Override
    public int compareTo(RelayState relayState) {
        return getName().compareTo(relayState.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelayState that = (RelayState) o;
        return id == that.id &&
                state == that.state &&
                Objects.equals(name, that.name) &&
                Objects.equals(source, that.source);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, source, state);
    }
}
