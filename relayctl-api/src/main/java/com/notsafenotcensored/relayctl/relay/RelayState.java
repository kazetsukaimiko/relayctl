package com.notsafenotcensored.relayctl.relay;

public class RelayState implements Comparable<RelayState> {
    private int id;
    private String name;
    private String source;
    private boolean state;

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

    public RelayState() {

    }

    public RelayState(Relay relay) {
        setId(relay.getId());
        setName(relay.getName());
        setSource(relay.getSource());
        setState(relay.getState());
    }

    @Override
    public int compareTo(RelayState relayState) {
        return getName().compareTo(relayState.getName());
    }
}
