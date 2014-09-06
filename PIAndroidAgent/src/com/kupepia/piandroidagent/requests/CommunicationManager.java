package com.kupepia.piandroidagent.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Base64;

public class CommunicationManager {

    private static CommunicationManager cm = null;
    private String ip;
    private static String USERNAME = "admin";
    private static String SIGN_IN_LOCATION = "/cgi-bin/toolkit/live_info.py?cmd=all_status";
    private String password = "";

    private CommunicationManager() {

    }

    public static CommunicationManager getInstance() {

        if ( cm == null ) {
            cm = new CommunicationManager();
        }
        return cm;
    }

    public void setRemoteHost( String IP ) {
        this.ip = IP;
    }

    public String getIP() {
        return this.ip;
    }

    public Response sendRequest( String location ) throws IOException,
            KeyManagementException, NoSuchAlgorithmException, JSONException {

        URL url = new URL( this.ip + location );

        HttpURLConnection httpConnection = (HttpsURLConnection) url
                .openConnection();

        TestPersistentConnection
                .setAcceptAllVerifier( (HttpsURLConnection) httpConnection );

        return sendRequest( location, (HttpsURLConnection) httpConnection );

    }

    public Response sendRequest( String location, HttpsURLConnection connection )
            throws IOException, JSONException {
        String userpass = USERNAME + ":" + password;
        String basicAuth = "Basic "
                + Base64.encodeToString( userpass.getBytes(), Base64.DEFAULT );
        connection.setRequestProperty( "Authorization", basicAuth );
        
        connection.connect();
        InputStream is = connection.getInputStream();

        BufferedReader streamReader = new BufferedReader(
                new InputStreamReader( is, "UTF-8" ) );
        StringBuilder responseStrBuilder = new StringBuilder();

        String inputStr;
        responseStrBuilder.append( "[" );
        while ( ( inputStr = streamReader.readLine() ) != null )
            responseStrBuilder.append( inputStr );
        responseStrBuilder.append( "]" );

        streamReader.close();
        JSONArray json = null;
        json = new JSONArray( responseStrBuilder.toString() );
        
        Response res = new Response( json, connection.getResponseCode() );
        return res;

    }

    public Response signIn( final String password ) throws IOException,
            KeyManagementException, NoSuchAlgorithmException, JSONException {

        String location = ip + SIGN_IN_LOCATION;

        this.password = password;

        URL url = new URL( location );

        HttpURLConnection httpConnection = (HttpsURLConnection) url
                .openConnection();

        TestPersistentConnection
                .setAcceptAllVerifier( (HttpsURLConnection) httpConnection );

        return sendRequest( location, (HttpsURLConnection) httpConnection );

    }

}
