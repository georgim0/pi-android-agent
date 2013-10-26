package com.kupepia.piandroidagent.test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.test.AndroidTestCase;

import com.kupepia.piandroidagent.R;
import com.kupepia.piandroidagent.requests.CommunicationManager;
import com.kupepia.piandroidagent.requests.Request;
import com.kupepia.piandroidagent.requests.RequestHandler;
import com.kupepia.piandroidagent.utils.XMLUtils;

public class TestRequest extends AndroidTestCase {
	
	public void testFillRequest() throws Throwable {
		Map<String, String> map = new HashMap<String, String>();
		map.put("uri://1", "1");
		map.put("uri://2", "2");
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder xmlBuilder = dbf.newDocumentBuilder();
		InputStream xmlInputStream = this.mContext.getResources()
				.openRawResource(R.raw.sample_request);
		Document xmlDocument = xmlBuilder.parse(xmlInputStream);
		
		Request request = RequestHandler.buildRequest(xmlDocument, map);
		
		Document reqDoc = request.getDocument();
		NodeList pairs = reqDoc.getElementsByTagName("pair");
		for (int i = 0; i < pairs.getLength(); i++)
		{
			Node pairNode = pairs.item(i);
			NodeList children = pairNode.getChildNodes();
			String keyFromReq = null;
			String valueFromReq = null;
			for (int j = 0; j < children.getLength(); j++)
			{
				Node childNode = children.item(j);
				if (childNode.getNodeName().equals("key"))
					keyFromReq = childNode.getTextContent();
				else if (childNode.getNodeName().equals("value"))
					valueFromReq = childNode.getTextContent();
			}
			boolean condition1 = keyFromReq.equals("uri://1") && 
					valueFromReq.equals("1");
			boolean condition2 = keyFromReq.equals("uri://2") && 
					valueFromReq.equals("2");
			assertTrue(condition1 || condition2);
		}
	}
	
	public void testCreateRequest() throws Throwable {
	
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("uri://1", "1");
		map.put("uri://2", "2");
		Request req = RequestHandler.buildRequest(map);
		Document reqDoc = req.getDocument();
		InputStream xmlInputStream = this.mContext.getResources()
				.openRawResource(R.raw.sample_request_template);
		Document templateDoc = XMLUtils.stream2Document(xmlInputStream);
		String xml = XMLUtils.Document2String(templateDoc);
		NodeList pairs = reqDoc.getElementsByTagName("pair");
		for (int i = 0; i < pairs.getLength(); i++)
		{
			Node pairNode = pairs.item(i);
			NodeList children = pairNode.getChildNodes();
			String keyFromReq = null;
			String valueFromReq = null;
			for (int j = 0; j < children.getLength(); j++)
			{
				Node childNode = children.item(j);
				if (childNode.getNodeName().equals("key"))
					keyFromReq = childNode.getTextContent();
				else if (childNode.getNodeName().equals("value"))
					valueFromReq = childNode.getTextContent();
			}
			boolean condition1 = keyFromReq.equals("uri://1") && 
					valueFromReq.equals("1");
			boolean condition2 = keyFromReq.equals("uri://2") && 
					valueFromReq.equals("2");
			assertTrue(condition1 || condition2);
		}
	}
	
	public void testSubmitRequestOnPowerManager() throws Throwable {
		String uri = "def://power_management";
		String value = "default";
		String username = "admin";
		String apikey="66rhb8N/pXAL6";
				
		try {	
			HashMap<String, String> map = new HashMap<String, String>();
			Request req = RequestHandler.buildRequest(map);
			map.put(uri, value);
			CommunicationManager cm = CommunicationManager.getInstance();
			req.addAuthentication(username, apikey);
			Document responseDoc = cm.sendRequest("/api-bin/power_management_android.py", req);
			String xmlString = XMLUtils.Document2String(responseDoc);
			assertTrue(xmlString.contains("response"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
			e.printStackTrace();
		}
	}
	
}
