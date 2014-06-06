package com.kupepia.piandroidagent.requests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

public class Response {
    private int code;
    private JSONArray body;
    
    protected Response(JSONArray json, int code) {
        this.code = code;
        this.body = json;
    }
    
    public int getCode() {
        return this.code;
    }
    
    public Object getBody() throws JSONException {
        return this.body.get(0);
    }
}
