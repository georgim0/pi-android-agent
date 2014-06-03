package com.kupepia.piandroidagent.test;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.kupepia.piandroidagent.requests.CommunicationManager;
import com.kupepia.piandroidagent.requests.Response;

import android.test.AndroidTestCase;

public class TestFeatures extends AndroidTestCase {

    public void test_file_manager_path_request() {
        String path="/home";
        try {
            CommunicationManager cm = CommunicationManager.getInstance();
            
            cm.setRemoteHost("https://82.102.116.227:8003");
            
            cm.signIn("");
            
            Response r = cm.sendRequest("/cgi-bin/toolkit/file_manager.py?path=/home");
            assertEquals(200, r.getCode());
            
            Object responseBody = r.getBody();
            JSONArray ja = null;
            
            if (responseBody instanceof JSONArray) {
                ja = (JSONArray)responseBody;
            }
            
            assertEquals(2, ja.length());
            
            assertTrue(ja.get(0) instanceof JSONObject);
            

            assertTrue(ja.getJSONObject(0).has("name"));
            
        } catch (Exception e) {
            fail();
        }
    }
    
}
