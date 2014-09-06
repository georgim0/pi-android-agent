package com.kupepia.piandroidagent.features.objects;

public enum StatusType {

    INSTALLED( "installed" ), UNINSTALLED( "uninstalled" ), ON( "on" ), OFF(
            "off" ), STATUS( "status");

    private String value;

    StatusType( String value ) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
