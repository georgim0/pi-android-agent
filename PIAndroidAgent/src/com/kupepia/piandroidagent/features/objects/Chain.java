package com.kupepia.piandroidagent.features.objects;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Chain {
    private final List<Rule> rules;
    private String defaultRule;
    private final String name;

    public Chain( String n ) {
        rules = new ArrayList<Rule>();
        this.name = n;
    }

    public String getDefaultRule() {
        return defaultRule;
    }

    public void setDefaultRule( String defaultRule ) {
        this.defaultRule = defaultRule;
    }

    public void addRule( Rule r ) {
        rules.add( r );
    }

    public Rule getRule( int index ) {
        return this.rules.get( index ).clone();
    }

    public int size() {
        return this.rules.size();
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return getName();
    }

    public TableLayout getView( Context c ) {

        TableLayout.LayoutParams tableParams =
                new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.WRAP_CONTENT,
                        TableLayout.LayoutParams.WRAP_CONTENT );

        TableLayout tableLayout = new TableLayout( c );
        tableLayout.setLayoutParams( tableParams );

        TextView tvProtocol = new TextView( c );
        
        TextView tvTarget = new TextView( c );
        TextView tvOtherInfo = new TextView( c );
        TextView tvDestination = new TextView( c );
        TextView tvSource = new TextView( c );
        TextView tvOption = new TextView( c );

        tvProtocol.setText( "Protocol" );
        tvProtocol.setTypeface(null, Typeface.BOLD);
        
        tvTarget.setText( "Target" );
        tvTarget.setTypeface(null, Typeface.BOLD);
        tvOtherInfo.setText( "Other info" );
        tvOtherInfo.setTypeface(null, Typeface.BOLD);
        tvDestination.setText( "Destination" );
        tvDestination.setTypeface(null, Typeface.BOLD);
        tvSource.setText( "Source" );
        tvSource.setTypeface(null, Typeface.BOLD);
        tvOption.setText( "Options" );
        tvOption.setTypeface(null, Typeface.BOLD);
        
        
        TableRow.LayoutParams rowParams =
                new TableRow.LayoutParams( TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT );
        
        int value = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 
                (float) 10, c.getResources().getDisplayMetrics());
        
        
        rowParams.rightMargin = value;
                
        TableRow tableRow = new TableRow( c );
        
        tableRow.addView( tvProtocol, rowParams );
        tableRow.addView( tvTarget, rowParams );
        tableRow.addView( tvOtherInfo, rowParams );
        tableRow.addView( tvDestination, rowParams );
        tableRow.addView( tvSource, rowParams );
        tableRow.addView( tvOption, rowParams );
        
        tableLayout.addView( tableRow );
        
        for ( Rule rule : this.rules ) {
            tableLayout.addView( rule.getView( c ) );
        }

        return tableLayout;
    }
}
