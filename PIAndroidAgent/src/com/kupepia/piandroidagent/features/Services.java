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
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.kupepia.piandroidagent.features.objects.ActionKeyType;
import com.kupepia.piandroidagent.requests.CommunicationManager;
import com.kupepia.piandroidagent.requests.Response;
import com.kupepia.piandroidagent.ui.ArrayAdapterUI;

public class Services extends ActionableFeatureUI {

    private static final String DATA_QUERY =
            "/cgi-bin/toolkit/live_info.py?cmd=services";

    Response response = null;
    Map<String, Boolean> result;

    private final String id;
    private Services myself;

    public Services() {
        super();
        result = new HashMap<String, Boolean>();
        id = "Services App";
        myself = this;
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
            configureSwitch( switchServiceStatus, serviceName );

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

    @Override
    public View getViewAfterAction( Response r ) {
        if ( r.getCode() == 0 )
            Toast.makeText( super.rlView.getContext(), "done",
                    Toast.LENGTH_LONG ).show();
        return null;
    }

    private void configureSwitch( Switch s, final String serviceName ) {
        s.setOnCheckedChangeListener( new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged( CompoundButton arg0, boolean arg1 ) {
                ActionKeyType akt =
                        arg1 ? ActionKeyType.ACTIVATE_SERVICE_QUERY
                                : ActionKeyType.DEACTIVATE_SERVICE_QUERY;
                myself.applyAction( akt.getValue() + serviceName );
            }

        } );
    }

}
