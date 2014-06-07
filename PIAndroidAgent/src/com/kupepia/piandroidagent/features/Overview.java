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
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class Overview extends FeatureUI {

    private static final String STATUS_QUERY =
            "/cgi-bin/toolkit/live_info.py?cmd=all_status";

    private String kernel = "N/A";
    private String hostname = "N/A";
    private boolean update = false;
    private int disk = -1;
    private int swap = -1;
    private int memory = -1;
    private double temp = -1;

    private String id;

    private JSONObject status = null;

    public Overview() {
        super();
        id = "System Overview";

    }

    @Override
    public void init() throws IOException, KeyManagementException,
            NoSuchAlgorithmException, JSONException {
        CommunicationManager cm = CommunicationManager.getInstance();

        Response response = cm.sendRequest( STATUS_QUERY );
        JSONObject statusJS = (JSONObject) ( response.getBody() );
        status = statusJS;
        // {"kernel": "3.10.25+", "temp": "45.5", "mem": 23, "hostname":
        // "192.168.1.85", "swap": 0, "disk": "40", "ucheck": true}
        kernel = statusJS.getString( "kernel" );
        hostname = statusJS.getString( "hostname" );

        update = statusJS.getBoolean( "ucheck" );

        disk = Integer.parseInt( statusJS.getString( "disk" ) );
        try {
            swap = statusJS.getInt( "swap" );
        }
        catch (NumberFormatException nfe) {
            
        }
        memory = statusJS.getInt( "mem" );

        temp = statusJS.getDouble( "temp" );

    }

    @Override
    public Object getResult() throws JSONException {
        return status;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public View getView( Context c ) {

        List<View> views = new ArrayList<View>();

        ListView lv = new ListView( c );

        views.add( getKernelView( c ) );
        views.add( getHostnameView( c ) );
        views.add( getMemView( c ) );
        views.add( getDiskView( c ) );
        views.add( getSwap(c) );
        views.add( getTempView( c ) );
        views.add( getUpdateView(c) );
        
        ArrayAdapter<View> adapter =
                new ArrayAdapterUI( c,
                        android.R.layout.simple_list_item_activated_1, views );

        lv.setAdapter( adapter );

        return lv;
    }

    private View getSwap( Context c ) {
        TextView tv = new TextView( c );
        int color = Color.GREEN;
        if (swap == -1) {
            tv.setText( "Swap: N/A" );
        } else {
            tv.setText( "Swap: " + swap + "%" );
            if (swap != 0) 
                color = Color.MAGENTA;
        }
        
        if (swap > 50) {
            color = Color.RED;
        }
        
        tv.setTextColor( color );

        return tv;
    }

    private View getUpdateView( Context c ) {
        TextView tvUpdate = new TextView(c);
        if (this.update) {
            tvUpdate.setText( "Updates are available" );
            tvUpdate.setTextColor( Color.MAGENTA );
        }
        else {
            
            tvUpdate.setText( "System is up to date" );
            tvUpdate.setTextColor( Color.GREEN );
        }
        return tvUpdate;
    }

    private View getTempView( Context c ) {
        TextView tv = new TextView( c );

        tv.setText( "Temperature: " + temp + "C" );

        int color = Color.BLACK;

        if ( temp < 1 ) {
            color = Color.BLACK;
        } else if ( temp < 51 ) {
            color = Color.GREEN;
        } else if ( temp < 60 ) {
            color = Color.MAGENTA;
        } else
            color = Color.RED;

        tv.setTextColor( color );

        return tv;

    }

    private View getDiskView( Context c ) {
        TextView tv = new TextView( c );

        tv.setText( "Disk: " + disk + "%" );

        int color = Color.BLACK;
        if ( disk < 1 ) {
            color = Color.BLACK;
        } else if ( disk > 1 && disk < 75 ) {
            color = Color.GREEN;
        } else if ( disk < 90 ) {
            color = Color.MAGENTA;
        } else
            color = Color.RED;

        tv.setTextColor( color );

        return tv;

    }

    private View getKernelView( Context c ) {

        TextView tv = new TextView( c );

        tv.setText( "Kernel: " + kernel );
        tv.setTextColor( Color.BLUE );
        return tv;
    }

    private View getHostnameView( Context c ) {
        TextView tv = new TextView( c );

        tv.setText( "Hostname: " + hostname );

        tv.setTextColor( Color.BLUE );
        return tv;
    }

    private View getMemView( Context c ) {

        TextView tv = new TextView( c );

        tv.setText( "Memory: " + memory + "%" );

        int color = Color.BLACK;
        if ( memory < 1 ) {
            color = Color.BLACK;
        } else if ( memory < 75 ) {
            color = Color.GREEN;
        } else if ( memory < 90 ) {
            color = Color.MAGENTA;
        } else
            color = Color.RED;

        tv.setTextColor( color );

        return tv;
    }

    public int getMemory() {

        return memory;

    }

    public int getDisk() {

        return disk;

    }

    public double getTemperature() {
        return this.temp;
    }

    public String getKernel() {
        return kernel;
    }

    public String getHostname() {
        return hostname;
    }

    public boolean getUpdateCheck() {
        return this.update;
    }

}
