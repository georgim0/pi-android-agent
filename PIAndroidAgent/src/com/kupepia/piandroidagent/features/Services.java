package com.kupepia.piandroidagent.features;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Switch;
import android.widget.TextView;

import com.kupepia.piandroidagent.requests.CommunicationManager;
import com.kupepia.piandroidagent.requests.Response;
import com.kupepia.piandroidagent.ui.ArrayAdapterUI;

public class Services extends FeatureUI {

    private static final String DATA_QUERY =
            "/cgi-bin/toolkit/live_info.py?cmd=services";
    private static final String ACTIVATE_SERVICE_QUERY =
            "/cgi-bin/toolkit/live_info.py?cmd=edit_service&param2=on&param1=";
    private static final String DEACTIVATE_SERVICE_QUERY =
            "/cgi-bin/toolkit/live_info.py?cmd=edit_service&param2=off&param1=";
    Response response = null;
    Map<String, Boolean> result;

    private final String id;

    public Services() {
        super();
        result = new HashMap<String, Boolean>();
        id = "Services App";
    }

    @Override
    public void init() throws IOException, KeyManagementException,
            NoSuchAlgorithmException, JSONException {

        CommunicationManager cm = CommunicationManager.getInstance();

        response = cm.sendRequest( DATA_QUERY );
        JSONObject responseBody = (JSONObject) response.getBody();
        JSONArray json_names = responseBody.names();

        for ( int i = 0; i < json_names.length(); i++ ) {
            String key = json_names.getString( i );
            boolean value = responseBody.getBoolean( key );
            result.put( key, value );
        }

    }

    @Override
    public Object getResult() throws JSONException {

        if ( response == null )
            return null;

        return response.getBody();
    }

    public Map<String, Boolean> getServices() {

        return result;
    }

    public Response activateService( String serviceName )
            throws KeyManagementException, NoSuchAlgorithmException,
            IOException, JSONException {
        String query = ACTIVATE_SERVICE_QUERY + serviceName;

        CommunicationManager cm = CommunicationManager.getInstance();
        Response response = cm.sendRequest( query );

        return response;

    }

    public Response deactivateService( String serviceName )
            throws KeyManagementException, NoSuchAlgorithmException,
            IOException, JSONException {
        String query = DEACTIVATE_SERVICE_QUERY + serviceName;

        CommunicationManager cm = CommunicationManager.getInstance();
        Response response = cm.sendRequest( query );

        return response;
    }

    @Override
    public String getID() {

        return this.id;

    }

    @Override
    public View getView( Context c ) {

        ListView lv = new ListView( c );
        List<View> views = new ArrayList<View>();

        for ( String serviceName : this.result.keySet() ) {
            RelativeLayout rl = new RelativeLayout( c );
            TextView tvServiceName = new TextView( c );
            tvServiceName.setText( serviceName );

            Switch switchServiceStatus = new Switch( c );
            switchServiceStatus.setChecked( this.result.get( serviceName ) );

            RelativeLayout.LayoutParams lp =
                    new RelativeLayout.LayoutParams( LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT );

            lp.addRule( RelativeLayout.ALIGN_PARENT_LEFT );

            rl.addView( tvServiceName, lp );

            lp =
                    new RelativeLayout.LayoutParams( LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT );
            lp.addRule( RelativeLayout.ALIGN_PARENT_RIGHT );

            rl.addView( switchServiceStatus, lp );
            views.add( rl );
        }

        ArrayAdapter<View> adapter =
                new ArrayAdapterUI( c,
                        android.R.layout.simple_list_item_activated_1, views );

        lv.setAdapter( adapter );

        return lv;
    }

}
