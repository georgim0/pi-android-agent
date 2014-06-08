package com.kupepia.piandroidagent.test;

import static com.kupepia.piandroidagent.test.SettingsForTests.address;
import static com.kupepia.piandroidagent.test.SettingsForTests.password;

import java.util.List;

import com.kupepia.piandroidagent.features.GPIO;
import com.kupepia.piandroidagent.features.objects.Pin;
import com.kupepia.piandroidagent.requests.CommunicationManager;

import android.test.AndroidTestCase;

public class TestGPIO extends AndroidTestCase {

    GPIO gpio = null;

    protected void setUp() throws Exception {
        super.setUp();
        gpio = new GPIO();
        CommunicationManager cm = CommunicationManager.getInstance();
        cm.setRemoteHost( address );
        cm.signIn( password );
    }

    public void test_init() {
        try {
            gpio.init();
        } catch ( Exception e ) {
            fail();
        }

        List<Pin> leftPins = gpio.getLeftPins();
        List<Pin> rightPins = gpio.getRightPins();

        assertTrue( leftPins.size() > 0 );
        assertTrue( rightPins.size() > 0 );
        int availableCounter = 0;
        for ( Pin pin : leftPins ) {
            int value = pin.getValue();
            if ( value == -1 ) {
                assertTrue( pin.getName().equals( pin.valueMsg() ) );
            } else {
                availableCounter++;
                assertTrue( pin.valueMsg().equals( "" + pin.getValue() ) );
                if ( value == 1 ) {
                    assertTrue( pin.active() );
                } else {
                    assertFalse( pin.active() );
                }
            }

        }

        assertEquals( 4, availableCounter );

        for ( Pin pin : leftPins ) {
            try {
                pin.getView( mContext );
            } catch ( Exception e ) {
                fail();
            }
        }

        try {
            gpio.getView( mContext );
        } catch ( Exception e ) {
            fail();
        }
    }

}
