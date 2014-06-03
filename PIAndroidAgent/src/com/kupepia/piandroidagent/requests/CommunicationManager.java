package com.kupepia.piandroidagent.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.HttpsURLConnection;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;


import android.content.Context;


public class CommunicationManager {

	private static CommunicationManager cm = null;
	private String ip;
	private static String USERNAME = "admin";
	private static String SIGN_IN_LOCATION = "/cgi-bin/toolkit/live_info.py?cmd=all_status";
	private Context context;
	private HttpClient client;
	private CommunicationManager()
	{
	    
	}
	
	public static CommunicationManager getInstance() throws KeyManagementException, UnrecoverableKeyException, 
	        NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException
	{
		if (cm == null)
		{
			cm = new CommunicationManager();
		}
		return cm;
	}
	
	public void setRemoteHost(String IP)
	{
		this.ip = IP;
	}
	
	public void setContext(Context mContext)
	{
		this.context = mContext;
	}
	
	public String getIP()
	{
		return this.ip;
	}
	
	public Response sendRequest(String location) throws ClientProtocolException, IOException, KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, CertificateException   {
	    
	    URL url = new URL(location);
	    
	    HttpURLConnection httpConnection = (HttpsURLConnection) url.openConnection();

        // Normally, instanceof would also be used to check the type.
        TestPersistentConnection.setAcceptAllVerifier((HttpsURLConnection)httpConnection);
        
        return sendRequest(location, (HttpsURLConnection) httpConnection);
        
	}
	
	public Response sendRequest(String location, HttpsURLConnection connection) throws ClientProtocolException, IOException, KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, CertificateException   {
	    
        /*client = createHttpClient();
        
	    HttpGet get = new HttpGet(location);
	    HttpResponse r =  client.execute(get);*/
	    
	    InputStream is = connection.getInputStream();
	    
	    BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8")); 
	    StringBuilder responseStrBuilder = new StringBuilder();

	    String inputStr;
	    while ((inputStr = streamReader.readLine()) != null)
	        responseStrBuilder.append(inputStr);
	    streamReader.close();
	    JSONObject js = null;
	    try {
            js = new JSONObject(responseStrBuilder.toString());
        } catch (JSONException e) {
            js = new JSONObject();
            try {
                js.put("code", "1");
            } catch (JSONException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
	    Response res = new Response(js, 200);
	    return res;
	}
	
	public Response signIn(final String password) throws ClientProtocolException, IOException, KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, CertificateException  {

	    String location = ip + "/" + SIGN_IN_LOCATION;
	    
	    Authenticator.setDefault (new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication ("admin", password.toCharArray());
            }
        });
        
	    
	    URL url = new URL(location);
        
	    
	    
        HttpURLConnection httpConnection = (HttpsURLConnection) url.openConnection();

        
        
        // Normally, instanceof would also be used to check the type.
        TestPersistentConnection.setAcceptAllVerifier((HttpsURLConnection)httpConnection);
        
        return sendRequest(location, (HttpsURLConnection) httpConnection);


	}
	
	
}
