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

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import android.os.StrictMode;
import android.test.AndroidTestCase;

import com.kupepia.piandroidagent.requests.CommunicationManager;
import com.kupepia.piandroidagent.requests.Response;

public class TestCommunicationManager extends AndroidTestCase {
	
	public void testSignIn(){
	    try {
	    
    	    CommunicationManager cm = CommunicationManager.getInstance();
    		cm.setRemoteHost("https://192.168.2.10:8003");
    		cm.setContext(this.getContext());
    		String password = ""; //TODO
			Response response = cm.signIn(password);
			assertEquals(200, response.getCode());
		}
		catch (Exception e)
		{
			fail();
		}
	}
	
	public void test_request() {
	    try {
	        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
	        String url = "https://www.google.com";
            CommunicationManager cm = CommunicationManager.getInstance();
            //localTrustStore.load(in, TRUSTSTORE_PASSWORD.toCharArray());
            Response r = cm.sendRequest(url);
            assertEquals(200, r.getCode());
            url = "https://192.168.2.10:8003";
            r = cm.sendRequest(url);
            assertEquals(200, r.getCode());
            
	    }
	    catch (Exception e) {
	        fail();
	    }
	}
}
