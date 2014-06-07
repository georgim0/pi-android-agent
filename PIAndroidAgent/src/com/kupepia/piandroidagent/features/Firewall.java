package com.kupepia.piandroidagent.features;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.json.JSONException;

import android.content.Context;
import android.view.View;

public class Firewall extends FeatureUI {

    private static final String FIREWALL_DATA_QUERY =
            "/cgi-bin/toolkit/pi_iptables_api.py";
    /*
     * {"FORWARD": {"rules": [{"protocol": "udplite", "target": "ACCEPT",
     * "otherinfo": "--", "destination": "ALL", "source": "64.12.34.12/32",
     * "option": "--"}], "default": "ACCEPT"}, "INPUT": {"rules": [], "default":
     * "ACCEPT"}, "fail2ban-ssh": {"rules": [], "default": "--"}, "OUTPUT":
     * {"rules": [], "default": "ACCEPT"}}
     */
    private List<Chain> chains;

    @Override
    public void init() throws IOException, KeyManagementException,
            NoSuchAlgorithmException, JSONException {
        // TODO Auto-generated method stub

    }

    @Override
    public Object getResult() throws JSONException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getID() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public View getView( Context c ) {
        // TODO Auto-generated method stub
        return null;
    }

}

class Chain {

    private List<Rule> rules;
    private String defaultRule;

    public String getDefaultRule() {
        return defaultRule;
    }

    public void setDefaultRule( String defaultRule ) {
        this.defaultRule = defaultRule;
    }

    public void addRule( Rule r ) {
        rules.add( r );
    }

    protected List<Rule> getRules() {
        return rules;
    }
}

class Rule {

    private String protocol;
    private String target;
    private String otherInfo;
    private String destination;
    private String source;
    private String option;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol( String protocol ) {
        this.protocol = protocol;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget( String target ) {
        this.target = target;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo( String otherInfo ) {
        this.otherInfo = otherInfo;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination( String destination ) {
        this.destination = destination;
    }

    public String getSource() {
        return source;
    }

    public void setSource( String source ) {
        this.source = source;
    }

    public String getOption() {
        return option;
    }

    public void setOption( String option ) {
        this.option = option;
    }

}