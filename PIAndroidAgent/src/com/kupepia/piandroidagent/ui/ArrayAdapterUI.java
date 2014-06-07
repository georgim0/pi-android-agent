package com.kupepia.piandroidagent.ui;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ArrayAdapterUI extends ArrayAdapter<View> {

    public ArrayAdapterUI( Context context, int resource,
            List<View> objects ) {
        super( context, resource, objects );
        
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        return this.getItem( position );
        
    }

}
