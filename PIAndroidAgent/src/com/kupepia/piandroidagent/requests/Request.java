package com.kupepia.piandroidagent.requests;

import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
		return xmlDocument.getTextContent();
	}

}
