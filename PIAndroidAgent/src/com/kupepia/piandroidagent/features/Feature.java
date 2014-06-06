package com.kupepia.piandroidagent.features;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.json.JSONException;

import android.content.Context;
import android.view.View;

public interface Feature {

    void init() throws IOException, KeyManagementException,
            NoSuchAlgorithmException, JSONException;

    Object getResult() throws JSONException;

    String getID();

    View getView(Context c);

}
