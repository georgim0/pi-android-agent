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
import android.graphics.Color;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.kupepia.piandroidagent.features.objects.ActionKeyType;
import com.kupepia.piandroidagent.requests.CommunicationManager;
import com.kupepia.piandroidagent.requests.Response;
import com.kupepia.piandroidagent.ui.ArrayAdapterUI;

public class Updates extends ActionableFeatureUI {

    private static final String QUERY_UPDATE = "/cgi-bin/toolkit/update_api.py";

    private static final int NO_ACTION = 0;

    private static final int BUSY = 201;

    private static final int UPDATE_PENDING = 100;

    private Map<String, String> packagesMap;
    private String id;
    private JSONObject result;

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
        this.result = packages;

        JSONObject packageList = packages.getJSONObject( "packages" );

        JSONArray packageNames = packageList.names();

        for ( int i = 0; i < packageNames.length(); i++ ) {
            String name = packageNames.getString( i );
            packagesMap.put( name, packageList.getString( name ) );
        }

    }

    @Override
    public Object getResult() throws JSONException {
        return this.result;
    }

    @Override
    public String getID() {

        return id;

    }

    @Override
    public View getView( Context c ) {

        if ( packagesMap.size() == 0 ) {
            try {
                return getOtherView( c );
            } catch ( JSONException je ) {
                return getErrorView( c );
            }
        }

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

        Button updateButton = createUpdateButton( c );

        parentView.addView( updateButton, lp );

        lp =
                new RelativeLayout.LayoutParams( LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT );

        lp.addRule( RelativeLayout.BELOW, updateButton.getId() );

        parentView.addView( lv, lp );

        return parentView;

    }

    private Button createUpdateButton( Context c ) {
        Button updateButton = new Button( c );
        updateButton.setId( 859230 );

        updateButton.setText( "Update" );

        updateButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                applyAction( ActionKeyType.PERFORM_UPDATE_QUERY.getValue() );
            }
        } );

        return updateButton;

    }

    private View getErrorView( Context c ) {
        TextView tv = new TextView( c );
        tv.setText( "Something went wrong!" );
        return tv;
    }

    private View getOtherView( Context c ) throws JSONException {

        int status = result.getInt( "status" );

        switch ( status ) {
            case NO_ACTION:
                return getOKView( c );

            case BUSY:
            case UPDATE_PENDING:
                return getBusyView( c );
            default:
                return getErrorView( c );

        }

    }

    private View getBusyView( Context c ) {
        TextView tv = new TextView( c );
        tv.setText( "The package manager is busy right now. Try again in a moment!" );
        return tv;
    }

    private View getOKView( Context c ) {

        RelativeLayout rl = new RelativeLayout( c );

        TextView tv = new TextView( c );
        tv.setText( "System is up to date!" );
        tv.setId( 9121 );

        Button checkButton = new Button( c );
        checkButton.setText( "Check for updates" );

        RelativeLayout.LayoutParams lp =
                new RelativeLayout.LayoutParams( LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT );

        rl.addView( tv, lp );
        lp =
                new RelativeLayout.LayoutParams( LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT );
        lp.addRule( RelativeLayout.ALIGN_PARENT_LEFT );
        lp.addRule( RelativeLayout.BELOW, tv.getId() );

        rl.addView( checkButton, lp );

        return rl;

    }

    public String toString() {
        return id;
    }

    @Override
    public View getViewAfterAction( Response r ) {

        TextView tv = new TextView( rlView.getContext() );
        tv.setText( "Update process initiated" );

        return tv;
    }

}
