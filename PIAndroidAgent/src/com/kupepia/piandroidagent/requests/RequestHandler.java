package com.kupepia.piandroidagent.requests;

import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
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
			ParserConfigurationException
	{
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		httppost.setHeader("xml", request.toString());
	    HttpResponse httpresponse = httpclient.execute(httppost);
	    String xmlResponse = EntityUtils.toString(httpresponse.getEntity());
	    Document d = XMLUtils.string2Document(xmlResponse);
	    return d;
	}
}
