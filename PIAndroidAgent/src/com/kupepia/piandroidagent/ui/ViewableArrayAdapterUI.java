package com.kupepia.piandroidagent.ui;

import java.util.List;

import com.kupepia.piandroidagent.features.objects.Viewable;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ViewableArrayAdapterUI<T extends Viewable> extends ArrayAdapter<T> {

    public ViewableArrayAdapterUI( Context context, int resource,
            List<T> objects ) {
        super( context, resource, objects );
        
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        return this.getItem( position ).getView( getContext() );
        
    }

}
