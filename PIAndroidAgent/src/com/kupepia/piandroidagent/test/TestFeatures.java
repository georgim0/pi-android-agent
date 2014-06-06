package com.kupepia.piandroidagent.test;

import org.json.JSONArray;
import org.json.JSONObject;

import com.kupepia.piandroidagent.requests.CommunicationManager;
import com.kupepia.piandroidagent.requests.Response;

import android.test.AndroidTestCase;

public class TestFeatures extends AndroidTestCase {
    private final String password = "";
     
    public void test_file_manager_path_request() {
        String path="/home/pi/";
        try {
            CommunicationManager cm = CommunicationManager.getInstance();
            
            cm.setRemoteHost("https://192.168.2.10:8003");
            
            cm.signIn(password);
            
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
