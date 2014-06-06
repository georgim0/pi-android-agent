package com.kupepia.piandroidagent.test;

import java.io.FileNotFoundException;

import org.json.JSONObject;

import android.test.AndroidTestCase;

import com.kupepia.piandroidagent.requests.CommunicationManager;
import com.kupepia.piandroidagent.requests.Response;
import static com.kupepia.piandroidagent.test.SettingsForTests.address;

import static com.kupepia.piandroidagent.test.SettingsForTests.password;

public class TestCommunicationManager extends AndroidTestCase {

    public void testSignIn() {
        try {

            CommunicationManager cm = CommunicationManager.getInstance();
            cm.setRemoteHost(address);
            Response response = cm.signIn(password);
            assertEquals(200, response.getCode());

            Object responseBody = response.getBody();
            JSONObject js = null;

            if (responseBody instanceof JSONObject) {
                js = (JSONObject) responseBody;
            } else {
                fail();
            }

            assertTrue(js.getInt("mem") > 0);

            String otherpassword = "123456";
            try {
                response = cm.signIn(otherpassword);

            } catch (FileNotFoundException e) {
                return;
            }
        } catch (Exception e) {
            fail();
        }
    }

    public void test_request() {
        try {

            CommunicationManager cm = CommunicationManager.getInstance();
            cm.setRemoteHost(address);
            cm.signIn(password);
            Response r = cm
                    .sendRequest("/cgi-bin/toolkit/live_info.py?cmd=all_status");
            assertEquals(200, r.getCode());

        } catch (Exception e) {
            fail();
        }
    }
}
