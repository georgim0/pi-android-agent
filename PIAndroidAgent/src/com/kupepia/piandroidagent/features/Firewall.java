package com.kupepia.piandroidagent.features;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kupepia.piandroidagent.objects.Chain;
import com.kupepia.piandroidagent.objects.Rule;
import com.kupepia.piandroidagent.requests.CommunicationManager;
import com.kupepia.piandroidagent.requests.Response;

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
    private String id = "Firewall management";

    public Firewall() {
        chains = new ArrayList<Chain>();
    }
    
    @Override
    public void init() throws IOException, KeyManagementException,
            NoSuchAlgorithmException, JSONException {

        CommunicationManager cm = CommunicationManager.getInstance();

        Response response = cm.sendRequest( FIREWALL_DATA_QUERY );

        JSONObject data = (JSONObject) response.getBody();

        JSONArray chainNames = data.names();

        for ( int i = 0; i < chainNames.length(); i++ ) {
            String chain = chainNames.getString( i );
            Chain firewallChain = new Chain(chain);

            JSONObject chainData = data.getJSONObject( chain );

            String defaultRule = chainData.getString( "default" );
            firewallChain.setDefaultRule( defaultRule );

            JSONArray rulesData = chainData.getJSONArray( "rules" );

            for ( int j = 0; j < rulesData.length(); j++ ) {

                JSONObject ruleJS = rulesData.getJSONObject( j );
                Rule rule = new Rule();
                rule.setDestination( ruleJS.getString( "destination" ) );
                rule.setOption( ruleJS.getString( "option" ) );
                rule.setOtherInfo( ruleJS.getString( "otherinfo" ) );
                rule.setProtocol( ruleJS.getString( "protocol" ) );
                rule.setSource( ruleJS.getString( "source" ) );
                rule.setTarget( ruleJS.getString( "target" ) );
                firewallChain.addRule( rule );
            }// for
            
            chains.add( firewallChain );

        }

    }

    @Override
    public List<Chain> getResult() throws JSONException {
        return chains;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public View getView( Context c ) {
        // TODO Auto-generated method stub
        return null;
    }

}
