package com.kupepia.piandroidagent.requests;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Response {
    private int code;
    private JSONTokener body;
    
    protected Response(JSONTokener json, int code) {
        this.code = code;
        this.body = json;
    }
    
    public int getCode() {
        return this.code;
    }
    
    public Object getBody() throws JSONException {
        return this.body.nextValue();
    }
}
