package com.kupepia.piandroidagent.requests;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.http.client.ClientProtocolException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;


public class CommunicationManager {

	private static CommunicationManager cm = null;
	private String ip;
	private static String USERNAME = "admin";
	private static String SIGN_IN_LOCATION = "api-bin/pi_api.py";
	
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
	
	public String getIP()
	{
		return this.ip;
	}
	
	public Document sendRequest(String location, Request request) throws 
		ClientProtocolException, IOException, SAXException, 
		ParserConfigurationException
	{
		return RequestHandler.submitRequest(this.ip+"/" + location, request);
	}
	
	public int signIn(String api_key) {
		//this method is a stab TODO
		api_key = "06ZWkfBxpdAUE";
		Request request;
		
		try {
			Document doc = this.createRequestDoc(api_key);
			
			//create new request
			request = new Request(doc);

			//submit request and get response
			Document response = this.sendRequest(SIGN_IN_LOCATION, request);
			
			int code = getResponseCode(response);
			return code;
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return -1;
		
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
	
}
