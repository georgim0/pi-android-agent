package com.kupepia.piandroidagent.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;


public class XMLUtils {
	
	public static Document string2Document(String xml) throws 
		SAXException, IOException, ParserConfigurationException
	{
	    InputStream is = new ByteArrayInputStream(xml.getBytes());
		return stream2Document(is);

	}
	
	public static Document file2Document(File file) throws 
		ParserConfigurationException, SAXException, IOException
	{
		InputStream xmlInputStream = new FileInputStream(file);
		return stream2Document(xmlInputStream);
	}
	
	public static Document stream2Document(InputStream xmlInputStream) throws 
		ParserConfigurationException, SAXException, IOException
	{

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlInputStream);
		return doc;
	}
}
