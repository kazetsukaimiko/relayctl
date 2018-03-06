package com.notsafenotcensored.relayctl.config;

import com.notsafenotcensored.relayctl.relay.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RelayConfig {
    private int id = -1;
    private String name = "";
    private String source = null;
    private List<Rule> rules = new ArrayList<>();

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

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public RelayConfig id(int id) {
        this.id = id; return this;
    }

    public RelayConfig name(String name) {
        this.name = name; return this;
    }

    public RelayConfig source(String source) {
        setSource(source); return this;
    }

    public RelayConfig(RelayConfig relayConfig) {
        this(
                relayConfig.getId(),
                relayConfig.getName(),
                relayConfig.getSource(),
                relayConfig.getRules()
        );
    }

    public RelayConfig(int id, String name, String source, List<Rule> rules) {
        this.id = id;
        this.name = name;
        this.source = source;
        this.rules = rules;
    }

    public RelayConfig() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelayConfig that = (RelayConfig) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
