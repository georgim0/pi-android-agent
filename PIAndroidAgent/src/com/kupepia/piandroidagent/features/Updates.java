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

import com.kupepia.piandroidagent.requests.CommunicationManager;
import com.kupepia.piandroidagent.requests.Response;
import com.kupepia.piandroidagent.ui.ArrayAdapterUI;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
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

        RelativeLayout parentView = new RelativeLayout( c );

        List<View> views = new ArrayList<View>();

        for ( String packageName : this.packagesMap.keySet() ) {
            RelativeLayout rl = new RelativeLayout( c );

            TextView tvPackageName = new TextView( c );
            TextView tvDescription = new TextView( c );

            tvPackageName.setText( packageName );
            tvDescription.setText( this.packagesMap.get( packageName ) );
            tvPackageName.setTextScaleX( 1.4f );
            tvPackageName.setTextColor( Color.BLUE );
            RelativeLayout.LayoutParams lp =
                    new RelativeLayout.LayoutParams( LayoutParams.MATCH_PARENT,
                            LayoutParams.WRAP_CONTENT );

            tvPackageName.setId( tvPackageName.hashCode() );

            rl.addView( tvPackageName, lp );
            lp =
                    new RelativeLayout.LayoutParams( LayoutParams.MATCH_PARENT,
                            LayoutParams.WRAP_CONTENT );
            lp.addRule( RelativeLayout.BELOW, tvPackageName.getId() );

            rl.addView( tvDescription, lp );

            views.add( rl );

        }

        ListView lv = new ListView( c );

        ArrayAdapter<View> adapter =
                new ArrayAdapterUI( c,
                        android.R.layout.simple_list_item_activated_1, views );

        lv.setAdapter( adapter );

        RelativeLayout.LayoutParams lp =
                new RelativeLayout.LayoutParams( LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT );

        lv.setId( lv.hashCode() );
        
        parentView.addView( lv, lp );
        
        Button updateButton = new Button(c);
        
        updateButton.setText( "Update" );
        
        lp = new RelativeLayout.LayoutParams( LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT );
        
        lp.addRule( RelativeLayout.BELOW, lv.getId() );
        
        parentView.addView( updateButton, lp );
        
        return parentView;

    }

    public void performUpdate() {

    }

    public String toString() {
        return id;
    }

}