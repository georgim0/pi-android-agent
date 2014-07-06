package com.kupepia.piandroidagent.features.objects;

public enum ActionKeyType {

    UPDATE_ACTION( "updateAction.py" ),

    // Services
    ACTIVATE_SERVICE_QUERY(
            "/cgi-bin/toolkit/live_info.py?cmd=edit_service&param2=on&param1=" ),

    DEACTIVATE_SERVICE_QUERY(
            "/cgi-bin/toolkit/live_info.py?cmd=edit_service&param2=off&param1=" ),

    // Update manager
    PERFORM_UPDATE_QUERY( "/cgi-bin/toolkit/update.py?action=update" ),

    // Package Recommendation
    GET_PACKAGE_INFO( "/cgi-bin/toolkit/packagerecommendations.py?index=" );

    private String value;

    ActionKeyType( String value ) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
