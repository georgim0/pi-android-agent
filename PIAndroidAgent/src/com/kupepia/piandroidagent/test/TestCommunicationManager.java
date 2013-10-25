package com.kupepia.piandroidagent.test;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import android.test.AndroidTestCase;

import com.kupepia.piandroidagent.requests.CommunicationManager;

public class TestCommunicationManager extends AndroidTestCase {



	public void testCreateRequestDoc() {
		CommunicationManager cm = CommunicationManager.getInstance();
		Document doc = cm.createRequestDoc("hello");

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = tf.newTransformer();

			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
					"yes");
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			String output = writer.getBuffer().toString()
					.replaceAll("\n|\r", "");

			String expectedOutput = "<request type=\"authenticate\">"
						+ "<username>admin</username>" 
						+ "<api-key>hello</api-key>"
						+ "</request>";
			
			assertTrue(output.equals(expectedOutput));
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testGetResponseCode(){
		CommunicationManager cm = CommunicationManager.getInstance();
		Document doc = null;
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			
			//create <response>
			Element root = doc.createElement("pi-api");
			doc.appendChild(root);
			root.setAttribute("version", "1.0");
			
			//create <response>
			Element response = doc.createElement("response");
			root.appendChild(response);
			
			//create <code>
			Element username = doc.createElement("code");
			root.appendChild(username);
			
			//set username = admin
			username.setTextContent("0");
			
			int code = cm.getResponseCode(doc);
			
			assertTrue(code==0);

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void testSignIn(){
		CommunicationManager cm = CommunicationManager.getInstance();
		cm.setRemoteHost("https://192.168.56.101:8005");
		cm.setContext(this.getContext());
		try {
			int responseCode = cm.signIn("");
			assertTrue(responseCode >= 0);
		}
		catch (Exception e)
		{
			fail();
		}
	}
}
