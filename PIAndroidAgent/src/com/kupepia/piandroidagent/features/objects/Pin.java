package com.kupepia.piandroidagent.features.objects;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Switch;
import android.widget.TextView;

public class Pin implements Viewable {

    private String direction;
    private int value = -1;
    private final String name;

    public Pin( String name ) {
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
        if ( value == -1 ) {
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

    public View getView( Context c ) {
        View view = null;
        if ( value == -1 ) {
            view = new View( c );
        } else {
            view = getAvailableView( c );
        }

        RelativeLayout rl = new RelativeLayout( c );

        RelativeLayout.LayoutParams lp =
                new RelativeLayout.LayoutParams( LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT );
        
        
        lp.addRule( RelativeLayout.ALIGN_LEFT);
        lp.addRule( RelativeLayout.CENTER_VERTICAL );
        
        TextView tvName = new TextView( c );
        
        tvName.setText( name );
        tvName.setBackgroundColor( Color.DKGRAY );
        tvName.setTextColor( Color.YELLOW );
        tvName.setTypeface( null, Typeface.BOLD_ITALIC );
        tvName.setTextScaleX( 1.2f );
        tvName.setId( 12912 );
        
        rl.addView( tvName, lp );
        
        lp =
                new RelativeLayout.LayoutParams( LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT );
        
        lp.addRule( RelativeLayout.ALIGN_PARENT_RIGHT );
        lp.addRule( RelativeLayout.CENTER_VERTICAL );
        rl.addView( view, lp );

        return rl;
    }

    private View getAvailableView( Context c ) {

        RelativeLayout rl = new RelativeLayout( c );

        Switch isActiveSwitch = new Switch( c );
        isActiveSwitch.setChecked( this.active() );

        Switch directionSwitch = new Switch( c );
        directionSwitch.setTextOn( "OUT" );
        directionSwitch.setTextOff( "IN" );

        directionSwitch.setId( 3920 );

        directionSwitch.setChecked( direction.equals( "OUT" ) );
       
        directionSwitch.setScaleX( 0.7f );
        directionSwitch.setScaleY( 0.8f );
        

        isActiveSwitch.setScaleX( 0.7f );
        isActiveSwitch.setScaleY( 0.8f );
        
        RelativeLayout.LayoutParams lp =
                new RelativeLayout.LayoutParams( LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT );

       rl.addView( directionSwitch, lp );

                lp =
                new RelativeLayout.LayoutParams( LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT );
        
        lp.addRule( RelativeLayout.RIGHT_OF, directionSwitch.getId());
        
        
        rl.addView( isActiveSwitch, lp );

        
        return rl;

    }

}
