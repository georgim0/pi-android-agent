package com.kupepia.piandroidagent.features;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kupepia.piandroidagent.features.objects.Chain;
import com.kupepia.piandroidagent.features.objects.Rule;
import com.kupepia.piandroidagent.requests.CommunicationManager;
import com.kupepia.piandroidagent.requests.Response;
import com.kupepia.piandroidagent.ui.AddIPTablesRule;
import com.kupepia.piandroidagent.ui.MainActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

public class Firewall extends FeatureUI {

    private static final String FIREWALL_DATA_QUERY =
            "/cgi-bin/toolkit/pi_iptables_api.py";
    private static final String FIRWALL_ADD_RULE =
            "/cgi-bin/toolkit/add_iptables_rule.py?";
    /*
     * {"FORWARD": {"rules": [{"protocol": "udplite", "target": "ACCEPT",
     * "otherinfo": "--", "destination": "ALL", "source": "64.12.34.12/32",
     * "option": "--"}], "default": "ACCEPT"}, "INPUT": {"rules": [], "default":
     * "ACCEPT"}, "fail2ban-ssh": {"rules": [], "default": "--"}, "OUTPUT":
     * {"rules": [], "default": "ACCEPT"}}
     */
    private List<Chain> chains;
    private String id = "Firewall management";

    private TableLayout chainView;
    private ArrayAdapter<Chain> adapter;

    private ScrollView sv;
    private TextView tvDefault;
    private int code;

    Button addRuleButton;
    private Spinner chainSpinner;

    public Firewall() {
        chains = new ArrayList<Chain>();
        code = 0;
    }

    @Override
    public void init() throws IOException, KeyManagementException,
            NoSuchAlgorithmException, JSONException {

        CommunicationManager cm = CommunicationManager.getInstance();

        Response response = cm.sendRequest( FIREWALL_DATA_QUERY );

        JSONObject data = (JSONObject) response.getBody();

        JSONArray chainNames = data.names();

        for ( int i = 0; i < chainNames.length(); i++ ) {
            String chain = chainNames.getString( i );
            Chain firewallChain = new Chain( chain );

            JSONObject chainData = data.getJSONObject( chain );

            String defaultRule = chainData.getString( "default" );
            firewallChain.setDefaultRule( defaultRule );

            JSONArray rulesData = chainData.getJSONArray( "rules" );

            for ( int j = 0; j < rulesData.length(); j++ ) {

                JSONObject ruleJS = rulesData.getJSONObject( j );
                Rule rule = new Rule();
                rule.setDestination( ruleJS.getString( "destination" ) );
                rule.setOption( ruleJS.getString( "option" ) );
                rule.setOtherInfo( ruleJS.getString( "otherinfo" ) );
                rule.setProtocol( ruleJS.getString( "protocol" ) );
                rule.setSource( ruleJS.getString( "source" ) );
                rule.setTarget( ruleJS.getString( "target" ) );
                firewallChain.addRule( rule );
            }// for

            chains.add( firewallChain );
        }

    }

    @Override
    public List<Chain> getResult() throws JSONException {
        return chains;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public View getView( Context c ) {
        if ( code == 0 ) {

            if ( chainSpinner == null ) {
                chainSpinner = new Spinner( c );
            }

            sv = new ScrollView( c );
            sv.setHorizontalScrollBarEnabled( true );

            if ( chainView == null )
                chainView = new TableLayout( c );

            if ( adapter == null )
                adapter =
                        new ArrayAdapter<Chain>( c,
                                android.R.layout.simple_spinner_dropdown_item,
                                chains );
            tvDefault = new TextView( c );
            if ( addRuleButton == null ) {
                addRuleButton = new Button( c );
            }
            chainSpinner.setAdapter( adapter );

            chainSpinner
                    .setOnItemSelectedListener( new OnItemSelectedListener() {

                        @Override
                        public void onItemSelected( AdapterView<?> parent,
                                View view, int position, long id ) {

                            Chain selectedChain = adapter.getItem( position );
                            sv.removeAllViews();
                            HorizontalScrollView hsv =
                                    new HorizontalScrollView( view.getContext() );

                            hsv.addView( selectedChain.getView( view
                                    .getContext() ) );
                            sv.addView( hsv );

                            setPolicyView( tvDefault,
                                    selectedChain.getDefaultRule() );
                        }

                        @Override
                        public void onNothingSelected( AdapterView<?> arg0 ) {
                            // TODO Auto-generated method stub

                        }

                    } );

            RelativeLayout rl = new RelativeLayout( c );

            RelativeLayout.LayoutParams lp =
                    new RelativeLayout.LayoutParams( LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT );

            lp.addRule( RelativeLayout.ALIGN_PARENT_LEFT );

            rl.addView( chainSpinner, lp );

            addRuleButton.setText( "Add rule" );
            addRuleButton.setOnClickListener( new View.OnClickListener() {

                @Override
                public void onClick( View v ) {

                    prepareAddRuleInterface( v.getContext() );

                }

            } );

            chainSpinner.setId( chainSpinner.hashCode() );

            tvDefault.setId( 18232 );

            lp =
                    new RelativeLayout.LayoutParams( LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT );

            lp.addRule( RelativeLayout.RIGHT_OF, chainSpinner.getId() );

            rl.addView( addRuleButton, lp );

            lp =
                    new RelativeLayout.LayoutParams( LayoutParams.MATCH_PARENT,
                            LayoutParams.WRAP_CONTENT );

            lp.addRule( RelativeLayout.BELOW, chainSpinner.getId() );

            lp.addRule( RelativeLayout.CENTER_HORIZONTAL );

            rl.addView( tvDefault, lp );

            lp =
                    new RelativeLayout.LayoutParams( LayoutParams.MATCH_PARENT,
                            LayoutParams.WRAP_CONTENT );

            lp.addRule( RelativeLayout.BELOW, tvDefault.getId() );

            lp.addRule( RelativeLayout.CENTER_HORIZONTAL );

            sv.addView( chainView );

            rl.addView( sv, lp );

            return rl;
        } else {

            TextView tv = new TextView( c );
            tv.setText( "Something is not right" );
            code = 0;
            return tv;

        }
    }

    protected void prepareAddRuleInterface( Context context ) {
        Intent addIpTablesRequest = new Intent(context, AddIPTablesRule.class);
        
        addIpTablesRequest.putExtra( "chain", chains.get( chainSpinner.getSelectedItemPosition() ).getName() );
        
        ( (Activity) context ).startActivityForResult(addIpTablesRequest, MainActivity.FIREWALL_CODE);
        
    }

    public static void setPolicyView( TextView tvDefault, String defaultRule ) {

        tvDefault.setText( "Default policy: " + defaultRule );
        int color = Color.BLUE;
        if ( defaultRule.equals( "ACCEPT" ) ) {
            color = Color.GREEN;
        } else if ( defaultRule.equals( "DROP" ) ) {
            color = Color.RED;
        }

        tvDefault.setTextColor( color );

    }

    @Override
    public void submitAction( Object... params ) {

        CommunicationManager cm = CommunicationManager.getInstance();

        String query = FIRWALL_ADD_RULE;
        for ( Object param : params ) {
            query += param + "&";
        }

        try {
            Response r = cm.sendRequest( query );
            JSONObject jsonResponse = (JSONObject) r.getBody();
            this.code = jsonResponse.getInt( "code" );

        } catch ( KeyManagementException e ) {

        } catch ( IOException e ) {

        } catch ( JSONException e ) {

        } catch ( Exception e ) {

        }

    }

}
