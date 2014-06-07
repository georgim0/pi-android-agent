package com.kupepia.piandroidagent.features;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kupepia.piandroidagent.requests.CommunicationManager;
import com.kupepia.piandroidagent.requests.Response;

import android.content.Context;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

public class Updates extends FeatureUI {

    private static final String QUERY_UPDATE = "/cgi-bin/toolkit/update_api.py";

    private Map<String, String> packagesMap;
    private String id;

    public Updates() {
        super();
        packagesMap = new HashMap<String, String>();
        id = "System updates";
    }

    @Override
    public void init() throws IOException, KeyManagementException,
            NoSuchAlgorithmException, JSONException {

        CommunicationManager cm = CommunicationManager.getInstance();
        Response response = cm.sendRequest( QUERY_UPDATE );

        Object json = response.getBody();

        JSONObject packages = (JSONObject) json;

        JSONObject packageList = packages.getJSONObject( "packages" );

        JSONArray packageNames = packageList.names();

        for ( int i = 0; i < packageNames.length(); i++ ) {
            String name = packageNames.getString( i );
            packagesMap.put( name, packageList.getString( name ) );
        }

    }

    @Override
    public Object getResult() throws JSONException {
        return this.packagesMap;
    }

    @Override
    public String getID() {

        return id;

    }

    @Override
    public View getView( Context c ) {

        GridLayout gl = new GridLayout( c );

        gl.setColumnCount( 2 );

        TextView tvpn = new TextView( c );
        TextView tvd = new TextView( c );

        tvpn.setText( "Package" );
        tvd.setText( "Description" );

        for ( String packageName : this.packagesMap.keySet() ) {
            TextView tvPackageName = new TextView( c );
            TextView tvDescription = new TextView( c );

            tvPackageName.setText( packageName );
            tvDescription.setText( this.packagesMap.get( packageName ) );

            gl.addView( tvPackageName );
            gl.addView( tvDescription );
        }

        return gl;
    }

    public void performUpdate() {

    }

    public String toString() {
        return id;
    }

}
