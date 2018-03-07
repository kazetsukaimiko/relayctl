package com.notsafenotcensored.relayctl.control;

import java.util.ArrayList;
import java.util.List;

public class ControlState {
    private String name;
    private String activeState;
    private List<String> availableStates = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActiveState() {
        return activeState;
    }

    public void setActiveState(String activeState) {
        this.activeState = activeState;
    }

    public List<String> getAvailableStates() {
        return availableStates;
    }

    public void setAvailableStates(List<String> availableStates) {
        this.availableStates = availableStates;
    }

    public ControlState() {

    }

    public ControlState(Control control) {
        setName(control.getName());
        setAvailableStates(new ArrayList<>(control.getStates().keySet()));
        setActiveState(getAvailableStates().stream().findFirst().orElse(null));
    }
}
