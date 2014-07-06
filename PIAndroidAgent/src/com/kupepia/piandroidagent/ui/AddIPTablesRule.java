package com.kupepia.piandroidagent.ui;

import com.kupepia.piandroidagent.R;
import com.kupepia.piandroidagent.R.layout;
import com.kupepia.piandroidagent.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AddIPTablesRule extends Activity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.add_iptables_dialog );
    }


}
