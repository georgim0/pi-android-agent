package com.kupepia.piandroidagent.features;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ViewSwitcher;

import com.kupepia.piandroidagent.features.objects.Pin;
import com.kupepia.piandroidagent.requests.CommunicationManager;
import com.kupepia.piandroidagent.requests.Response;
import com.kupepia.piandroidagent.ui.ArrayAdapterUI;
import com.kupepia.piandroidagent.ui.ViewableArrayAdapterUI;

public class GPIO extends FeatureUI {

    private static final String DATA_QUERY =
            "/cgi-bin/toolkit/gpio_manager_api.py";

    private List<Pin> leftPins;
    private List<Pin> rightPins;
    private JSONArray data;

    private final String id;

    public GPIO() {
        leftPins = new ArrayList<Pin>();
        rightPins = new ArrayList<Pin>();
        id = "GPIO";
    }

    @Override
    public void init() throws IOException, KeyManagementException,
            NoSuchAlgorithmException, JSONException {

        CommunicationManager cm = CommunicationManager.getInstance();

        Response response = cm.sendRequest( DATA_QUERY );

        data = (JSONArray) response.getBody();

        JSONArray leftPins = data.getJSONArray( 0 );
        JSONArray rightPins = data.getJSONArray( 1 );

        for ( int i = 0; i < leftPins.length(); i++ ) {
            JSONObject pinJS = leftPins.getJSONObject( i );
            Pin pin = new Pin( pinJS.getString( "name" ) );
            pin.setDirection( pinJS.getString( "direction" ) );
            try {
                pin.setValue( pinJS.getInt( "value" ) );
            } catch ( JSONException je ) {
                pin.setValue( -1 );
            }
            this.leftPins.add( pin );

        }

        for ( int i = 0; i < rightPins.length(); i++ ) {
            JSONObject pinJS = rightPins.getJSONObject( i );
            Pin pin = new Pin( pinJS.getString( "name" ) );
            pin.setDirection( pinJS.getString( "direction" ) );
            try {
                pin.setValue( pinJS.getInt( "value" ) );
            } catch ( JSONException je ) {
                pin.setValue( -1 );
            }
            this.rightPins.add( pin );

        }

    }

    @Override
    public Object getResult() throws JSONException {

        return data;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public View getView( Context c ) {

        ListView leftPinsListView = new ListView( c );
        ViewableArrayAdapterUI<Pin> adapterL =
                new ViewableArrayAdapterUI<Pin>( c,
                        android.R.layout.simple_list_item_1, leftPins );

        leftPinsListView.setAdapter( adapterL );

        ListView rightPinsListView = new ListView( c );
        ViewableArrayAdapterUI<Pin> adapterR =
                new ViewableArrayAdapterUI<Pin>( c,
                        android.R.layout.simple_list_item_1, rightPins );

        rightPinsListView.setAdapter( adapterR );

        return leftPinsListView;
    }

    public List<Pin> getLeftPins() {
        return leftPins;
    }

    public List<Pin> getRightPins() {
        return rightPins;
    }

}
