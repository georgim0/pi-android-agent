package com.kupepia.piandroidagent.test;

import java.util.List;

import org.json.JSONException;

import com.kupepia.piandroidagent.features.Firewall;
import com.kupepia.piandroidagent.objects.Chain;
import com.kupepia.piandroidagent.objects.Rule;
import com.kupepia.piandroidagent.requests.CommunicationManager;

import android.test.AndroidTestCase;

import static com.kupepia.piandroidagent.test.SettingsForTests.password;

import static com.kupepia.piandroidagent.test.SettingsForTests.address;

public class TestFirewall extends AndroidTestCase {

    Firewall f = null;

    @Override
    protected void setUp() throws Exception {
        f = new Firewall();
        CommunicationManager cm = CommunicationManager.getInstance();
        cm.setRemoteHost( address );
        cm.signIn( password );
    }

    public void test_init() {

        try {
            f.init();
        } catch ( Exception e ) {
            fail();
        }

        try {
            List<Chain> chains = f.getResult();
            
            for (Chain chain : chains) {
                assertTrue(chain.getName() != null);
                assertTrue(chain.getDefaultRule() != null);
                for (int i = 0; i < chain.size(); i++) {
                    Rule r = chain.getRule( i );
                    assertTrue(r.getDestination() != null);
                    assertTrue(r.getOption() != null);
                    assertTrue(r.getOtherInfo() != null);
                    assertTrue(r.getProtocol() != null);
                    assertTrue(r.getSource() != null);
                    assertTrue(r.getTarget() != null);
                    
                    //test mutability
                    r.setDestination( "1234" );
                    r = chain.getRule( i );
                    assertFalse(r.getDestination().equals( "1234" ));
                    
                }
            }
            
        } catch ( JSONException e ) {
            fail();
        }
        
    }

}
