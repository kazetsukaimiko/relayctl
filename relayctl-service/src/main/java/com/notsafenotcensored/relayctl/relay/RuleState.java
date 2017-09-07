package com.notsafenotcensored.relayctl.relay;

public enum RuleState {
    OFF, // I don't do anything if the relay isn't off.
    ON,  // I don't do anything if the relay isn't on.
    FORCE_OFF, // If the relay is ON, I force to OFF before changing my state.
    FORCE_ON   // IF the relay is OFF, I force to ON before changing my state.
}
