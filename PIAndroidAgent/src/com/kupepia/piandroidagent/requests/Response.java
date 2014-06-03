package com.kupepia.piandroidagent.requests;

import org.json.JSONObject;

public class Response {
    private int code;
    private JSONObject body;
    
    protected Response(JSONObject body, int code) {
        this.code = code;
        this.body = body;
    }
    
    public int getCode() {
        return this.code;
    }
    
    public JSONObject getBody() {
        return this.body;
    }
}
