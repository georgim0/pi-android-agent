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
			Node pairNode = pairNodes.item(i);
			NodeList keyValueNodes = pairNode.getChildNodes();
			String nodeValue = null;
			String key = null;
			Node valueNode = null;
			for (int j = 0; j < keyValueNodes.getLength(); j++)
			{
				String tagName = keyValueNodes.item(j).getNodeName();
				nodeValue = keyValueNodes.item(j).getTextContent();
				if (tagName.equals("key"))
				{
					key = keyValueNodes.item(j).getTextContent(); 
					nodeValue = keyValuePair.get(key);
				}
				else if (tagName.equals("value"))
					valueNode = keyValueNodes.item(j);
			}//for j
			
			if (keyValuePair.containsKey(key))
				valueNode.setTextContent(keyValuePair.put(key, nodeValue));
		
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
		el_username.setNodeValue(username);
		
		Element el_apikey = 
				this.xmlDocument.createElement("api-key");
		el_apikey.setNodeValue(apikey);
		
		el_auth.appendChild(el_username);
		el_auth.appendChild(el_apikey);
		
		this.xmlDocument.appendChild(el_auth);
				
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
			el_value.setNodeValue(pairs.get(key));
			el_key.setNodeValue(key);
			el_pair.appendChild(el_key);
			el_pair.appendChild(el_value);
			rootElement.appendChild(el_pair);
		}//for
		d.appendChild(rootElement);
		return new Request(d);
	}

}
