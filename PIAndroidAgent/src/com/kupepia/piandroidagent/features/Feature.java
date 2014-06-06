package com.kupepia.piandroidagent.features;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.json.JSONException;

public interface Feature {
    
    void init() throws IOException, KeyManagementException, NoSuchAlgorithmException, JSONException;
    
    Object getResult() throws JSONException;
    
}
