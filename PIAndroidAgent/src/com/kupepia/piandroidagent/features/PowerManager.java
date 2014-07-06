package com.kupepia.piandroidagent.features;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.json.JSONException;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.kupepia.piandroidagent.requests.CommunicationManager;


public class PowerManager extends FeatureUI {

    private String id;
    
    private static final String VIEW_QUERY = "/cgi-bin/toolkit/power_manager.py?type=js";
    
    public PowerManager() {
        id = "Power Manager";
    }
    
    @Override
    public void init() throws IOException, KeyManagementException,
            NoSuchAlgorithmException, JSONException {
        
        CommunicationManager cm = CommunicationManager.getInstance();
        cm.sendRequest( VIEW_QUERY );

    }

    @Override
    public Object getResult() throws JSONException {
        return null;
    }

    @Override
    public String getID() {
        
        return id;
    }

    @Override
    public View getView( Context c ) {
        TextView tv = new TextView(c);
        tv.setText( "I am useless" );
        return tv;
    }

    @Override
    public <T> void submitAction( T... params ) {
        // TODO Auto-generated method stub

    }

}
