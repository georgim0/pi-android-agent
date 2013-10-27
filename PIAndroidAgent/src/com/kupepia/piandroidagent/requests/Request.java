package com.kupepia.piandroidagent.requests;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.kupepia.piandroidagent.utils.XMLUtils;

public class Request {
	
	private Document xmlDocument;
	
	protected Request(Document doc)
	{
		this.xmlDocument = doc;
	}
	
	public void fillRequest(Map<String, String> keyValuePair)
	{
		NodeList pairNodes = xmlDocument.getElementsByTagName("pair");
		for (int i = 0; i < pairNodes.getLength(); i++)
		{
			Element el_pair = (Element) pairNodes.item(i);
			Element el_key = (Element) el_pair.getElementsByTagName("key").item(0);
			Element el_value = (Element) el_pair.getElementsByTagName("value").item(0);
			el_value.setTextContent(keyValuePair.get(el_key.getTextContent()));
		}//for i
		
	}//fillRequest
	
	public Document getDocument()
	{
		return xmlDocument;
	}
	
	public String toString()
	{
		return XMLUtils.Document2String(this.xmlDocument);
	}
	
	public void addAuthentication(String username, String apikey)
	{
		Element el_auth = 
				this.xmlDocument.createElement("authentication");
		
		Element el_username = 
				this.xmlDocument.createElement("username");
		el_username.setTextContent(username);
		
		Element el_apikey = 
				this.xmlDocument.createElement("api-key");
		el_apikey.setTextContent(apikey);
		
		el_auth.appendChild(el_username);
		el_auth.appendChild(el_apikey);
		
		this.xmlDocument.getFirstChild().appendChild(el_auth);
				
	}
	
	public static Request createRequest(HashMap<String, String> pairs) throws ParserConfigurationException
	{
		DocumentBuilder db = 
				DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document d = db.newDocument();
		Element rootElement = d.createElement("request");
		rootElement.setAttribute("version", "1.0");
		for (String key:pairs.keySet())
		{
			Element el_pair = d.createElement("pair");
			Element el_key = d.createElement("key");
			Element el_value = d.createElement("value");
			el_value.setTextContent(pairs.get(key));
			el_key.setTextContent(key);
			el_pair.appendChild(el_key);
			el_pair.appendChild(el_value);
			rootElement.appendChild(el_pair);
		}//for
		d.appendChild(rootElement);
		return new Request(d);
	}
	
	public static Request createFormatRequest(String file) throws ParserConfigurationException
	{
		DocumentBuilder db = 
				DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document d = db.newDocument();
		Element rootElement = d.createElement("request");
		rootElement.setAttribute("version", "1.0");
		Element el_format = d.createElement("format");
		el_format.setTextContent(file);
		rootElement.appendChild(el_format);
		d.appendChild(rootElement);
		return new Request(d);
	}

}
