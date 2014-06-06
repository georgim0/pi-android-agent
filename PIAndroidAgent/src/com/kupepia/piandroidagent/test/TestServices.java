package com.kupepia.piandroidagent.test;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.kupepia.piandroidagent.features.Services;
import com.kupepia.piandroidagent.requests.CommunicationManager;
import com.kupepia.piandroidagent.requests.Response;

import android.test.AndroidTestCase;

import static com.kupepia.piandroidagent.test.SettingsForTests.password;

import static com.kupepia.piandroidagent.test.SettingsForTests.address;

public class TestServices extends AndroidTestCase {

    Services s = null;
    
    @Override
    protected void setUp() throws Exception {
        s = new Services();
        CommunicationManager cm = CommunicationManager.getInstance();
        cm.setRemoteHost(address);
        cm.signIn(password);
    }
    
    public void test_service_request() {
        
        try {
            s.init();
        }
        catch (Exception e) {
            fail();
        }
        
        Object result = null;
        try {
            result = s.getResult();
        } catch (JSONException e) {
            fail();
        }
        assertTrue(result instanceof JSONObject);
        
        assertTrue(s.getServices() instanceof HashMap);
    
    }
    
    public void test_service_on() {
        String serviceName = "apache2";
        
        try {
            Response r = s.activateService(serviceName);
            assertEquals(200, r.getCode());
        } catch (Exception e) {
            fail();
        }
        
        try {
            s.init();
        } catch (Exception e) {
            fail();
        }
        Map<String, Boolean> servicesMap = s.getServices();
        assertTrue(servicesMap.get("apache2"));
        
        
        
    }
    
    public void test_service_off() {
        String serviceName = "apache2";
        
        try {
            Response r = s.deactivateService(serviceName);
            assertEquals(200, r.getCode());
        } catch (Exception e) {
            fail();
        }
        
        try {
            s.init();
        } catch (Exception e) {
            fail();
        }
        Map<String, Boolean> servicesMap = s.getServices();
        assertFalse(servicesMap.get("apache2"));
        
    }
    
}
