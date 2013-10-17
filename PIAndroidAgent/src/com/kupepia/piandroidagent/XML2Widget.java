package com.kupepia.piandroidagent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.w3c.dom.Element;

import android.content.Context;

public class XML2Widget {
	private Context mContext;
	private ArrayList<Widget> widgets;
	public XML2Widget(Context mContext) throws FileNotFoundException {
		this.mContext = mContext;
		File xmlFile = new File("sample1.xml");

		if (!xmlFile.exists()) {
			throw new FileNotFoundException();
		}
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);

			// optional, but recommended
			// read this -
			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("widget");
			  
			for (int temp = 0; temp < nList.getLength(); temp++) {
		 
				Node nNode = nList.item(temp);
		 
				System.out.println("\nCurrent Element :" + nNode.getNodeName());
		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		 
					Element eElement = (Element) nNode;
		 
					System.out.println("Widget type : " + eElement.getAttribute("type"));
					System.out.println("'Uri : " + eElement.getElementsByTagName("uri").item(0).getTextContent());
					System.out.println("Label : " + eElement.getElementsByTagName("label").item(0).getTextContent());
		 
				}
			}
			
			NodeList nList2 = doc.getElementsByTagName("submit");
			Node nNode = nList2.item(0);
			 
			System.out.println("\nCurrent Element :" + nNode.getNodeName());
			Element eElement = (Element) nNode;
			 
			System.out.println("Staff id : " + eElement.getAttribute("text"));
			System.out.println("First Name : " + eElement.getElementsByTagName("url").item(0).getTextContent());
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
