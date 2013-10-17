package com.kupepia.piandroidagent;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class XML2Widget {
	
	private Context mContext;
	private ArrayList<Widget> widgets;
		
	public XML2Widget(Context mContext) throws FileNotFoundException {
		this.mContext = mContext;
		
		//File xmlFile = new File("assets/sample1.xml");
		InputStream xmlFile = this.mContext.getResources().openRawResource(R.raw.sample1);
		/*
		if (!xmlFile.exists()) {
			throw new FileNotFoundException();
		}
		*/
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);

			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("widget");
			  
			for (int temp = 0; temp < nList.getLength(); temp++) {
		 
				Node nNode = nList.item(temp);
		 
				//System.out.println("\nCurrent Element :" + nNode.getNodeName());
		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		 
					Element eElement = (Element) nNode;
					Log.w("XML", eElement.getElementsByTagName("label").item(0).getTextContent());
					//System.out.println("Widget type : " + eElement.getAttribute("type"));
					//System.out.println("'Uri : " + eElement.getElementsByTagName("uri").item(0).getTextContent());
					//System.out.println("Label : " + eElement.getElementsByTagName("label").item(0).getTextContent());
					if (eElement.getAttribute("type").equals("textview")){
						TextViewDisplayWidget widget = new TextViewDisplayWidget(eElement.getElementsByTagName("label").item(0).getTextContent());
						widgets.add(widget);
					}
				}
			}
			
			NodeList nList2 = doc.getElementsByTagName("submit");
			Node nNode = nList2.item(0);
			 
			//System.out.println("\nCurrent Element :" + nNode.getNodeName());
			Element eElement = (Element) nNode;
			 
			//System.out.println("Staff id : " + eElement.getAttribute("text"));
			//System.out.println("First Name : " + eElement.getElementsByTagName("url").item(0).getTextContent());
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public ArrayList<Widget> getWidgets() {
		return widgets;
	}

	public View getView(){
		TextView tv = new TextView(mContext);
		tv.setText("koupepia");
		return tv;
		
	}

}
