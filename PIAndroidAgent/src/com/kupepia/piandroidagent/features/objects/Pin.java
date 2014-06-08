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
            view = getDisabledView( c );
        } else {
            view = getAvailableView( c );
        }

        RelativeLayout rl = new RelativeLayout( c );

        RelativeLayout.LayoutParams lp =
                new RelativeLayout.LayoutParams( LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT );
        
        
        lp.addRule( RelativeLayout.ALIGN_PARENT_LEFT);
        
        TextView tvName = new TextView( c );
        
        tvName.setText( name );
        tvName.setBackgroundColor( Color.DKGRAY );
        tvName.setTextColor( Color.YELLOW );
        tvName.setTypeface( null, Typeface.BOLD_ITALIC );
        
        tvName.setId( 12912 );
        
        rl.addView( tvName, lp );
        
        lp =
                new RelativeLayout.LayoutParams( LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT );
        
        lp.addRule( RelativeLayout.ALIGN_PARENT_RIGHT);
        
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
       
        directionSwitch.setTextScaleX( 0.6f );
        isActiveSwitch.setTextScaleX( 0.6f );
        
        int dipValue =
                (int) TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP,
                        (float) 1, c.getResources().getDisplayMetrics() );

        
        directionSwitch.setHeight( dipValue );
        isActiveSwitch.setHeight( dipValue );
        dipValue =
                (int) TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP,
                        (float) 2, c.getResources().getDisplayMetrics() );

        
        directionSwitch.setWidth( dipValue );
        isActiveSwitch.setWidth( dipValue );
        
        RelativeLayout.LayoutParams lp =
                new RelativeLayout.LayoutParams( LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT );

        lp.addRule( RelativeLayout.ALIGN_PARENT_LEFT);


        rl.addView( directionSwitch, lp );

        lp =
                new RelativeLayout.LayoutParams( LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT );
        
        lp.addRule( RelativeLayout.ALIGN_PARENT_RIGHT);
        
        
        rl.addView( isActiveSwitch, lp );

        return rl;

    }

    private View getDisabledView( Context c ) {

        TextView tvDirection = new TextView( c );

        tvDirection.setText( direction );
        tvDirection.setBackgroundColor( Color.BLACK );
        tvDirection.setTextColor( Color.WHITE );
        tvDirection.setTypeface( null, Typeface.BOLD );

        return tvDirection;
    }

}
