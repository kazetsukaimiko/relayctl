package com.notsafenotcensored.relayctl.relay;

import java.util.Objects;

public abstract class Relay implements Comparable<Relay> {

    public abstract int getId();
    public abstract String getName();
    public abstract String getSource();
    public abstract boolean getState();
    public abstract boolean setState(boolean state);

    public boolean on() {
        return setState(true);
    }
    public boolean off() {
        return setState(false);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Relay that = (Relay) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int compareTo(Relay relay) {
        return String.valueOf(getName())
                .compareTo(String.valueOf(relay != null ? relay.getName() : null));
    }
}