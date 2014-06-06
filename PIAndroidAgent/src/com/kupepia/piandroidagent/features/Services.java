package com.kupepia.piandroidagent.features;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kupepia.piandroidagent.requests.CommunicationManager;
import com.kupepia.piandroidagent.requests.Response;

public class Services implements Feature {

    private static final String DATA_QUERY = "/cgi-bin/toolkit/live_info.py?cmd=services";
    private static final String ACTIVATE_SERVICE_QUERY = "/cgi-bin/toolkit/live_info.py?cmd=edit_service&param2=on&param1=";
    private static final String DEACTIVATE_SERVICE_QUERY = "/cgi-bin/toolkit/live_info.py?cmd=edit_service&param2=off&param1=";
    Response response = null;
    Map<String, Boolean> result;
    
    public Services() {
        result = new HashMap<String, Boolean>();
    }
    
    @Override
    public void init() throws IOException, KeyManagementException, NoSuchAlgorithmException, JSONException {
        
        CommunicationManager cm = CommunicationManager.getInstance();
        
        response = cm.sendRequest(DATA_QUERY);
        JSONObject responseBody = (JSONObject)response.getBody();
        JSONArray json_names = responseBody.names();
        
        for (int i = 0; i < json_names.length(); i++) {
            String key = json_names.getString(i);
            boolean value = responseBody.getBoolean(key);
            result.put(key, value);
        }
        
    }

    @Override
    public Object getResult() throws JSONException {
        
        if (response == null)
            return null;
        
        return response.getBody();
    }

    public Map<String, Boolean> getServices() {
        
        return result;
    }

    public Response activateService(String serviceName) throws KeyManagementException, NoSuchAlgorithmException, IOException, JSONException {
        String query = ACTIVATE_SERVICE_QUERY + serviceName;
        
        CommunicationManager cm = CommunicationManager.getInstance();
        Response response = cm.sendRequest(query);
        
        return response;
        
    }

    public Response deactivateService(String serviceName) throws KeyManagementException, NoSuchAlgorithmException, IOException, JSONException {
        String query = DEACTIVATE_SERVICE_QUERY + serviceName;
        
        CommunicationManager cm = CommunicationManager.getInstance();
        Response response = cm.sendRequest(query);
        
        return response;
    }

}
