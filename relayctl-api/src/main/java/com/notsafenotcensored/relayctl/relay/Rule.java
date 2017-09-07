package com.notsafenotcensored.relayctl.relay;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Rule {
    private boolean myState = true;
    private int them = -1;
    private boolean theirState = true;

    public boolean isMyState() {
        return myState;
    }

    public void setMyState(boolean myState) {
        this.myState = myState;
    }

    public int getThem() {
        return them;
    }

    public void setThem(int them) {
        this.them = them;
    }

    public boolean isTheirState() {
        return theirState;
    }

    public void setTheirState(boolean theirState) {
        this.theirState = theirState;
    }

    public Rule() {
    }
}
