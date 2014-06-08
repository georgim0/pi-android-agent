package com.kupepia.piandroidagent.features.objects;

import android.content.Context;
import android.view.View;

public class Pin {
  
    private String direction;
    private int value = -1;
    private final String name;
    
    public Pin(String name) {
        this.name = name;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection( String direction ) {
        this.direction = direction;
    }

    public int getValue() {
        return value;
    }
    
    public boolean active() {
        return value == 1;
    }
    
    public String valueMsg() {
        if (value == -1) {
            return name;
        } else
            return "" + value;
    }

    public void setValue( int value ) {
        this.value = value;
    }

    public String getName() {
        return name;
    }
    
    public View getView(Context c) {
        return null;
    }
    
    
}
