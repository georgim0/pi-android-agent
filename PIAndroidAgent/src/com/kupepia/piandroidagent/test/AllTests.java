package com.kupepia.piandroidagent.test;

import junit.framework.TestSuite;
import junit.framework.Test;
import android.test.suitebuilder.TestSuiteBuilder;

public class AllTests extends TestSuite {

    public static Test suite() {
        return new TestSuiteBuilder(AllTests.class)
                .includeAllPackagesUnderHere().build();
    }
}
