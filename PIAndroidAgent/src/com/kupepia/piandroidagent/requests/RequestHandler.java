package com.kupepia.piandroidagent.requests;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import android.content.Context;

import com.kupepia.piandroidagent.R;

public class RequestHandler {

	public static Document getDoc(Context c) throws Exception{
		InputStream xmlInputStream = c.getResources()
				.openRawResource(R.raw.sample1);

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlInputStream);
		
		return doc;
	}
}
