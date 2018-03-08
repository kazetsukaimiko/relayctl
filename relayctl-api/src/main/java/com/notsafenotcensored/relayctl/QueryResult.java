package com.notsafenotcensored.relayctl;

import com.notsafenotcensored.relayctl.control.ControlState;
import com.notsafenotcensored.relayctl.relay.RelayState;

import java.util.List;

public class QueryResult {
    private List<ControlState> controls;
    private List<RelayState> relays;

    public List<ControlState> getControls() {
        return controls;
    }

    public void setControls(List<ControlState> controls) {
        this.controls = controls;
    }

    public List<RelayState> getRelays() {
        return relays;
    }

    public void setRelays(List<RelayState> relays) {
        this.relays = relays;
    }

    public QueryResult(List<ControlState> controls, List<RelayState> relays) {
        this.controls = controls;
        this.relays = relays;
    }

    public QueryResult() {
    }
}
