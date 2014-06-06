package com.kupepia.piandroidagent.test;

import org.json.JSONArray;
import org.json.JSONObject;

import com.kupepia.piandroidagent.requests.CommunicationManager;
import com.kupepia.piandroidagent.requests.Response;

import android.test.AndroidTestCase;

import static com.kupepia.piandroidagent.test.SettingsForTests.password;

import static com.kupepia.piandroidagent.test.SettingsForTests.address;

public class TestFeatures extends AndroidTestCase {

    public void test_file_manager_path_request() {
        String path = "/home/pi/";
        try {
            CommunicationManager cm = CommunicationManager.getInstance();

            cm.setRemoteHost(address);

            cm.signIn(password);

            Response r = cm
                    .sendRequest("/cgi-bin/toolkit/file_manager.py?path="
                            + path);
            assertEquals(200, r.getCode());

            Object responseBody = r.getBody();
            JSONArray ja = null;

            if (responseBody instanceof JSONArray) {
                ja = (JSONArray) responseBody;
            }

            assertEquals(2, ja.length());

            assertTrue(ja.get(0) instanceof JSONObject);

            assertTrue(ja.getJSONObject(0).has("name"));

        } catch (Exception e) {
            fail();
        }
    }

}
