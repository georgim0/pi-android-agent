package com.kupepia.piandroidagent.objects;

import java.util.ArrayList;
import java.util.List;


public class Chain {
    private final List<Rule> rules;
    private String defaultRule;
    private final String name;

    public Chain( String n ) {
        rules = new ArrayList<Rule>();
        this.name = n;
    }

    public String getDefaultRule() {
        return defaultRule;
    }

    public void setDefaultRule( String defaultRule ) {
        this.defaultRule = defaultRule;
    }

    public void addRule( final Rule r ) {
        rules.add( r );
    }
    
    public Rule getRule(int index) {
        return this.rules.get( index ).clone();
    }
    
    public int size() {
        return this.rules.size();
    }

    public String getName() {
        return name;
    }

}
