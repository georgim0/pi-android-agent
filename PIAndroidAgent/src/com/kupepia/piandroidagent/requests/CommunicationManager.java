package com.kupepia.piandroidagent.requests;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.client.ClientProtocolException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class CommunicationManager {

	private static CommunicationManager cm = null;
	private String ip;
	
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
	
	public boolean signIn() {
		//this method is a stab TODO
		return false;
		
	}
	
}
