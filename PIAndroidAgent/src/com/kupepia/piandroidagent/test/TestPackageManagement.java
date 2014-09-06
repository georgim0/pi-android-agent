package com.kupepia.piandroidagent.test;

import static com.kupepia.piandroidagent.test.SettingsForTests.address;
import static com.kupepia.piandroidagent.test.SettingsForTests.password;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.test.AndroidTestCase;
import android.util.Log;

import com.kupepia.piandroidagent.features.PackageManagement;
import com.kupepia.piandroidagent.features.objects.ActionKeyType;
import com.kupepia.piandroidagent.features.objects.PackageDetails;
import com.kupepia.piandroidagent.features.objects.StatusType;
import com.kupepia.piandroidagent.requests.CommunicationManager;

public class TestPackageManagement extends AndroidTestCase {

    PackageManagement pm = null;

    protected void setUp() throws Exception {
        super.setUp();
        this.pm = new PackageManagement();
        CommunicationManager cm = CommunicationManager.getInstance();
        cm.setRemoteHost( address );
        cm.signIn( password );
    }

//    public void test_init() {
//        try {
//            pm.init();
//        } catch ( Exception e ) {
//            fail();
//        }
//    }

    public void test_initialisePackageListInfo() {
        Map<String, PackageDetails> packageListInfo =
                new HashMap<String, PackageDetails>();

        int i = 1;
        String query = ActionKeyType.PACKAGE_INFO_QUERY.getValue() + i++;
        Log.w("com.kupepia", query);
        Object data =
                pm.executeQuery( query );
        
        if ( ! ( data instanceof JSONArray ) ) {
            return;
        }
        JSONObject jsonObject;

        try {
            jsonObject = ( (JSONArray) data ).getJSONObject( 0 );// there is
                                                                 // only one
            Boolean status =
                    jsonObject.getBoolean( StatusType.INSTALLED.getValue() );
            assertNotNull( status ); assertTrue( status );
            String version = jsonObject.getString( "Version" );
            String description = jsonObject.getString( "Description" );
            String packName = jsonObject.getString( "Package Name" );

            packageListInfo.put( packName, new PackageDetails( status, version,
                    packName, description ) );

        } catch ( JSONException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*
     * public void test_fetchFirstPackage() { String[] headings = { "Status",
     * "Description", "Package Name", "Version", "installed" };
     * 
     * pm.setQueryParam( "?index=1" ); JSONArray data = (JSONArray)
     * pm.executeQuery(); for ( int i = 0; i < data.length(); i++ ) { try {
     * JSONObject jsonObject = data.getJSONObject( i ); JSONArray names =
     * jsonObject.names(); for ( int j = 0; j < names.length(); j++ ) { String
     * name = names.getString( j ); String headingName = headings[j];
     * assertNotNull( names ); assertEquals( headingName, name );
     * 
     * String value = jsonObject.getString( name );
     * 
     * assertNotNull( value ); assertNotSame( "", value.toString() ); } } catch
     * ( JSONException e ) { // TODO Auto-generated catch block
     * e.printStackTrace(); } } }
     * 
     * public void test_checkStopResponse() { pm.setQueryParam(
     * "?action=getPackageList" ); JSONArray packListData = (JSONArray)
     * pm.executeQuery();
     * 
     * Integer invalidIndex = packListData.length() + 1; // Try to fetch an out
     * // of bounds package
     * 
     * pm.setQueryParam( "?index=" + invalidIndex.toString() ); JSONObject data
     * = (JSONObject) pm.executeQuery(); try { String actualName =
     * data.names().getString( 0 ); String actualValue = data.getString(
     * actualName ); String expectedName = "STOP"; String expectedValue =
     * "There are no more packages to load";
     * 
     * assertEquals( expectedName, actualName ); assertEquals( expectedValue,
     * actualValue );
     * 
     * } catch ( JSONException e ) { // TODO Auto-generated catch block
     * e.printStackTrace(); } }
     * 
     * public void test_getPackageList() { pm.setQueryParam(
     * "?action=getPackageList" ); JSONArray data = (JSONArray)
     * pm.executeQuery(); for ( int i = 0; i < data.length(); i++ ) { try {
     * JSONObject jsonObject = data.getJSONObject( i ); JSONArray names =
     * jsonObject.names(); String name = names.getString( 0 );// there is only
     * one name String value = jsonObject.getString( name ); assertNotNull(
     * names ); assertEquals( "label", name ); assertNotNull( value );
     * assertNotSame( "", value.toString() );
     * 
     * } catch ( JSONException e ) { // TODO Auto-generated catch block
     * e.printStackTrace(); } } }
     */
}
