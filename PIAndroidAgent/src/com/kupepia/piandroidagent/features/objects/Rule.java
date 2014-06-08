package com.kupepia.piandroidagent.features.objects;

import com.kupepia.piandroidagent.features.Firewall;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

public class Rule {

    private String protocol;
    private String target;
    private String otherInfo;
    private String destination;
    private String source;
    private String option;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol( String protocol ) {
        this.protocol = protocol;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget( String target ) {
        this.target = target;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo( String otherInfo ) {
        this.otherInfo = otherInfo;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination( String destination ) {
        this.destination = destination;
    }

    public String getSource() {
        return source;
    }

    public void setSource( String source ) {
        this.source = source;
    }

    public String getOption() {
        return option;
    }

    public void setOption( String option ) {
        this.option = option;
    }

    public Rule clone() {
        Rule r = new Rule();
        r.destination = destination;
        r.option = option;
        r.otherInfo = otherInfo;
        r.protocol = protocol;
        r.source = source;
        r.target = target;
        return r;
    }

    public View getView( Context c ) {

        TableRow.LayoutParams rowParams =
                new TableRow.LayoutParams( TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT );
        
        int value = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 
                (float) 10, c.getResources().getDisplayMetrics());
        
        
        rowParams.rightMargin = value;
                
        TableRow tableRow = new TableRow( c );

        TextView tvProtocol = new TextView( c );
        TextView tvTarget = new TextView( c );
        TextView tvOtherInfo = new TextView( c );
        TextView tvDestination = new TextView( c );
        TextView tvSource = new TextView( c );
        TextView tvOption = new TextView( c );

        tvProtocol.setText( protocol );
        
        Firewall.setPolicyView( tvTarget, target );
        tvTarget.setText( target );
        
        tvOtherInfo.setText( otherInfo );
        tvDestination.setText( destination );
        tvSource.setText( source );
        tvOption.setText( option );

        tableRow.addView(tvProtocol, rowParams);
        tableRow.addView(tvTarget, rowParams);
        tableRow.addView(tvOtherInfo, rowParams);
        tableRow.addView(tvDestination, rowParams);
        tableRow.addView( tvSource, rowParams );
        tableRow.addView(tvOption, rowParams);
        
        return tableRow;

    }

}
