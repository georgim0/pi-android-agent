package com.kupepia.piandroidagent.features.objects;

public enum StatusType {
    
    INSTALLED("installed"), UNINSTALLED("uninstalled");
    
    private String value;

    StatusType( String value ) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
