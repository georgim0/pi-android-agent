package com.kupepia.piandroidagent.features;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.kupepia.piandroidagent.features.objects.PackageDetails;
import com.kupepia.piandroidagent.features.objects.PackageEntryViews;
import com.kupepia.piandroidagent.features.objects.StatusType;
import com.kupepia.piandroidagent.requests.CommunicationManager;
import com.kupepia.piandroidagent.requests.Response;
import com.kupepia.piandroidagent.ui.ArrayAdapterUI;

public class PackageManagement extends LazyLoadingFeatureUI {

    private final String id;
    private static final String QUERY_PATH =
            "/cgi-bin/toolkit/package_recommendations.py";
    private String queryParam = "?action=getPackageList";
    private PackageManagement myself;
    private Map<String, PackageDetails> packageListInfo = null;
    private List<View> views = null;
    private Map<String, PackageEntryViews> packageEntryViews = null;
    private List<String> packageListInfoKeys = new ArrayList<String>();

    public PackageManagement() {
        super();
        packageListInfo = new HashMap<String, PackageDetails>();
        this.id = "Package recommendations";
        packageEntryViews = new HashMap<String, PackageEntryViews>();
        myself = this;
    }

    @Override
    public void init() {
        Object packListData = executeQuery( QUERY_PATH + this.queryParam );
        if ( packListData instanceof JSONArray ) {
            this.packageListInfo = initialisePackListNames( packListData );
        }
    }

    private Map<String, PackageDetails> initialisePackListNames(
            Object packListData ) {
        JSONArray packList = (JSONArray) packListData;
        Map<String, PackageDetails> packNamesList =
                new HashMap<String, PackageDetails>();
        for ( int i = 0; i < packList.length(); i++ ) {
            JSONObject jsonObject;
            try {
                jsonObject = packList.getJSONObject( i );

                JSONArray names = jsonObject.names();
                String name = names.getString( 0 );// there is only one name
                String value = jsonObject.getString( name );
                packNamesList.put( value, null );
            } catch ( JSONException e ) {
                e.printStackTrace();
            }
        }
        return packNamesList;
    }

    private void updatePackListInfo( Object data ) {
        if ( ! ( data instanceof JSONObject ) ) {
            return; // we got the STOP response
        }
        JSONObject jsonObject;
        String packName = "";
        try {
            jsonObject = ( (JSONObject) data );
            JSONArray keys = jsonObject.names();
            for ( int i = 0; i < keys.length(); i++ ) {

                packName = keys.getString( i );
                JSONObject jo = (JSONObject) jsonObject.get( packName );
                Boolean status = jo.getBoolean( StatusType.STATUS.getValue() );
                PackageDetails packDetails =
                        new PackageDetails( status, "", packName, "" );
                packageListInfoKeys.add( packName );
                packageListInfo.put( packName, packDetails );
            }

        } catch ( JSONException e ) {
            e.printStackTrace();
        }
    }

    public Object executeQuery( String query ) {
        CommunicationManager cm = CommunicationManager.getInstance();

        Response response = null;
        try {
            response = cm.sendRequest( query );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        try {
            if ( response.getBody() instanceof JSONArray ) {
                return (JSONArray) response.getBody();
            } else if ( response.getBody() instanceof JSONObject )
                return (JSONObject) response.getBody();
        } catch ( JSONException e ) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object getResult() throws JSONException {
        return null;
    }

    @Override
    public String getID() {
        return this.id;
    }

    @Override
    public View getView( Context c ) {

        ListView lv = new ListView( c );
        views = new ArrayList<View>();
        Set<String> keys = this.packageListInfo.keySet();
        for ( String packName : keys ) {
            PackageEntryViews record = new PackageEntryViews();

            RelativeLayout rl = new RelativeLayout( c );
            TextView tvPackName = new TextView( c );
            tvPackName.setText( packName );
            record.setTvPackName( tvPackName );

            TextView tvDescription = new TextView( c );
            tvDescription.setText( "Loading. . ." );
            record.setTvDescription( tvDescription );

            Switch switchPackStatus = new Switch( c );
            switchPackStatus.setVisibility( View.INVISIBLE );
            record.setSwitchPackStatus( switchPackStatus );

            // switchPackStatus.setChecked( this.packageListInfo.get( packName )
            // .getInstallStatus() );
            // configureSwitch( switchPackStatus, packName );

            RelativeLayout.LayoutParams lp =
                    new RelativeLayout.LayoutParams( LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT );

            lp.addRule( RelativeLayout.ALIGN_PARENT_LEFT );

            rl.addView( record.getTvPackName(), lp );

            lp =
                    new RelativeLayout.LayoutParams( LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT );
            lp.addRule( RelativeLayout.ALIGN_PARENT_RIGHT );

            rl.addView( switchPackStatus, lp );
            rl.addView( record.getTvDescription(), lp );

            views.add( rl );
            packageEntryViews.put( packName, record );
        }

        ArrayAdapter<View> adapter =
                new ArrayAdapterUI( c,
                        android.R.layout.simple_list_item_activated_1, views );

        lv.setAdapter( adapter );

        // lazy loading the first index = 1
        // applyAction( ActionKeyType.PACKAGE_INFO_QUERY.getValue() + counter );
        // in bg
        initLazyLoading();

        return lv;

    }

    private void initLazyLoading() {
        List<String> packageList = set2List( packageListInfo.keySet() );
        List<List<String>> chunks = getChunked( packageList, 10 );
        for ( List<String> aList : chunks ) {
            String stringify =
                    aList.toString().replace( "[", "['" )
                            .replace( ", ", "', '" ).replace( "]", "']" )
                            .replace( " ", "%20" );
            applyAction( ActionKeyType.PACKAGE_MASS_INFO_QUERY.getValue()
                    + stringify );
        }
        // applyAction in bg

    }

    private List<String> set2List( Set<String> keySet ) {
        List<String> list = new ArrayList<String>();
        list.addAll( keySet );
        return list;
    }

    private List<List<String>> getChunked( List<String> list, int chunkSize ) {
        List<List<String>> chunks = new LinkedList<List<String>>();

        for ( int i = 0; i < list.size(); i += chunkSize ) {
            chunks.add( list.subList( i,
                    i + Math.min( chunkSize, list.size() - i ) ) );
        }
        return chunks;
    }

    @Override
    public View getViewAfterAction( Response r ) {
        if ( r == null ) {
            return null;
        }
        try {
            // packName will be initialised if the packListInfo was updated
            updatePackListInfo( r.getBody() );
        } catch ( JSONException e1 ) {
            e1.printStackTrace();
        }

        for ( String packName : packageListInfoKeys ) {

            Switch sw = packageEntryViews.get( packName ).getSwitchPackStatus();
            sw.setVisibility( View.VISIBLE );
            sw.setChecked( packageListInfo.get( packName ).getInstallStatus() );
            configureSwitch( sw, packName );
            packageEntryViews.get( packName ).getTvDescription()
                    .setVisibility( View.INVISIBLE );

        }

        if ( r.getCode() == 0 ) {
            Toast.makeText( super.rlView.getContext(), "done",
                    Toast.LENGTH_LONG ).show();
            // Log.w( "getViewAfterAction_bg_code", "done" );
        }

        return null;
    }

    private void configureSwitch( Switch s, final String packName ) {
        s.setOnCheckedChangeListener( new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged( CompoundButton arg0, boolean arg1 ) {

                ActionKeyType akt =
                        arg1 ? ActionKeyType.PACKAGE_INSTALL_QUERY
                                : ActionKeyType.PACKAGE_UNINSTALL_QUERY;
                myself.applyAction( akt.getValue() + packName );
            }

        } );
    }

}
