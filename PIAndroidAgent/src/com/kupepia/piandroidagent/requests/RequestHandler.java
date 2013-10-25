package com.kupepia.piandroidagent.requests;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.kupepia.piandroidagent.utils.XMLUtils;

public class RequestHandler {

	public static Request buildRequest(Document d, Map<String, String> map) 
			throws Exception{
		Request request = new Request(d);
		request.fillRequest(map);
		return request;
	}
	
	protected static Document submitRequest(String url, Request request) 
			throws ClientProtocolException, IOException, SAXException, 
			ParserConfigurationException, KeyManagementException, 
			UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, 
			CertificateException
	{
		HttpClient httpclient = CommunicationManager.createHttpClient();
		HttpPost httppost = new HttpPost(url);
		//httppost.addHeader("xml", request.toString());
		BasicNameValuePair bnvp = new BasicNameValuePair("xml", request.toString());
	    List<NameValuePair> postList = new ArrayList<NameValuePair>();
	    postList.add(bnvp);
		httppost.setEntity(new UrlEncodedFormEntity(postList));
		HttpResponse httpresponse = httpclient.execute(httppost);
	    String xmlResponse = EntityUtils.toString(httpresponse.getEntity());
	    Document d = XMLUtils.string2Document(xmlResponse);
	    return d;
	}
}
