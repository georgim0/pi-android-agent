package com.kupepia.piandroidagent.test;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import org.json.JSONException;

import com.kupepia.piandroidagent.features.Updates;
import com.kupepia.piandroidagent.requests.CommunicationManager;

import android.test.AndroidTestCase;

import static com.kupepia.piandroidagent.test.SettingsForTests.password;

import static com.kupepia.piandroidagent.test.SettingsForTests.address;

public class TestUpdates extends AndroidTestCase {

    Updates u = null;

    @Override
    protected void setUp() throws Exception {
        u = new Updates();
        CommunicationManager cm = CommunicationManager.getInstance();
        cm.setRemoteHost(address);
        cm.signIn(password);
    }

    public void test_init() {

        try {
            u.init();
        } catch (Exception e) {
            fail();
        }

        try {
            assertTrue(u.getResult() instanceof HashMap);
        } catch (JSONException e) {
            fail();
        }

    }

}
