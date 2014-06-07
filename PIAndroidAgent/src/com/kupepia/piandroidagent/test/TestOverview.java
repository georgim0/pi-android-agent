package com.kupepia.piandroidagent.test;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import org.json.JSONException;

import com.kupepia.piandroidagent.features.Overview;
import com.kupepia.piandroidagent.requests.CommunicationManager;

import android.test.AndroidTestCase;

import static com.kupepia.piandroidagent.test.SettingsForTests.password;

import static com.kupepia.piandroidagent.test.SettingsForTests.address;

public class TestOverview extends AndroidTestCase {

    Overview o = null;

    @Override
    protected void setUp() throws Exception {
        o = new Overview();
        CommunicationManager cm = CommunicationManager.getInstance();
        cm.setRemoteHost( address );
        cm.signIn( password );
    }

    public void test_init() {

        try {
            o.init();
        } catch ( Exception e ) {
            fail();
        }

        assertTrue( o.getMemory() > 0 );
        assertTrue( o.getDisk() > 0 );
        assertTrue( o.getTemperature() > 0);
        assertTrue(o.getKernel() instanceof String);
        assertTrue(o.getHostname() instanceof String);
        assertTrue( o.getUpdateCheck() || ! o.getUpdateCheck());

    }

}
