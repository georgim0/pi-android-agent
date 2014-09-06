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
    PACKAGE_INFO_QUERY( "/cgi-bin/toolkit/package_recommendations.py?index=" ), PACKAGE_INSTALL_QUERY(
            "/cgi-bin/toolkit/installUninstallPackage.py?action=install&packageName=" ), PACKAGE_UNINSTALL_QUERY(
            "/cgi-bin/toolkit/installUninstallPackage.py?action=uninstall&packageName=" );

    private String value;

    ActionKeyType( String value ) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
