package com.kupepia.piandroidagent.requests;

import java.io.IOException;
import java.io.InputStream;
import java.security.Certificate;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.kupepia.piandroidagent.R;

import android.content.Context;


public class CommunicationManager {

	private static CommunicationManager cm = null;
	private String ip;
	private static String USERNAME = "admin";
	private static String SIGN_IN_LOCATION = "api-bin/pi_api.py";
	private Context context;
	private final static String TRUSTSTORE_PASSWORD = "pi-android-api";
	private CommunicationManager()
	{
		
	}
	
	public static CommunicationManager getInstance()
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
	
	public Document sendRequest(String location, Request request) throws 
		ClientProtocolException, IOException, SAXException, 
		ParserConfigurationException, KeyManagementException, 
		UnrecoverableKeyException, NoSuchAlgorithmException, 
		KeyStoreException, CertificateException
	{
		return RequestHandler.submitRequest(this.ip+"/" + location, request);
	}
	
	public int signIn(String api_key) throws 
		ClientProtocolException, IOException, SAXException, ParserConfigurationException, 
		KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, 
		KeyStoreException, CertificateException 
	{
		Request request;
		
		Document doc = this.createRequestDoc(api_key);
		
		//create new request
		request = new Request(doc);

		//submit request and get response
		Document response = this.sendRequest(SIGN_IN_LOCATION, request);
		
		int code = getResponseCode(response);
		return code;
			

		
	}
	
	public Document createRequestDoc(String api_key){
		Document doc = null;
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			
			//create <request>
			Element root = doc.createElement("request");
			doc.appendChild(root);
			
			//add attribute type = authenticate
			root.setAttribute("type", "authenticate");
			
			//create <username>
			Element username = doc.createElement("username");
			root.appendChild(username);
			
			//set username = admin
			username.setTextContent(USERNAME);
			
			//create <api-key>
			Element apikey = doc.createElement("api-key");
			root.appendChild(apikey);
			
			//set api-key = api_key;
			apikey.setTextContent(api_key);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    return doc;
	}
	
	public int getResponseCode(Document response){
		response.getDocumentElement().normalize();
		Element pi_api = response.getDocumentElement();
		Element responseElement = (Element) pi_api.getElementsByTagName("response").item(0);
		String code = pi_api.getElementsByTagName("code").item(0).getTextContent();
		
		return Integer.parseInt(code);
	}
	
	protected static HttpClient createHttpClient() throws 
		KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, 
		KeyStoreException, CertificateException, IOException
	{
		KeyStore localTrustStore = KeyStore.getInstance("BKS");
		InputStream in = cm.context.getResources().openRawResource(R.raw.cacerts);
		localTrustStore.load(in, TRUSTSTORE_PASSWORD.toCharArray());
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		SSLSocketFactory sslSocketFactory = new SSLSocketFactory(localTrustStore);
		HttpParams params = new BasicHttpParams();
		
	    sslSocketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		schemeRegistry.register(new Scheme("https", sslSocketFactory, 8005));

		ClientConnectionManager cm = 
		    new ThreadSafeClientConnManager(params, schemeRegistry);

		HttpClient client = new DefaultHttpClient(cm, params);

		return client;
	}
}
