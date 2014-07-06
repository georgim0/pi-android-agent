package com.kupepia.piandroidagent.features;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

import com.kupepia.piandroidagent.requests.CommunicationManager;
import com.kupepia.piandroidagent.requests.Response;

public abstract class ActionableFeatureUI extends FeatureUI {
    private String query;
    private final ActionableFeatureUI myself;

    public ActionableFeatureUI() {
        myself = this;
    }

    private void setNewQuery( String query ) {
        this.query = query;
    }

    public void applyAction( String query ) {
        setNewQuery( query );
        ActionableFeatureTask aft = new ActionableFeatureTask();
        aft.execute();
    }

    private Response action() throws KeyManagementException,
            NoSuchAlgorithmException, IOException, JSONException {

        CommunicationManager cm = CommunicationManager.getInstance();
        Response r = cm.sendRequest( query );
        return r;
    }

    public abstract View getViewAfterAction( Response r );

    public class ActionableFeatureTask extends AsyncTask<Void, Void, Void> {

        private ProgressDialog dialog;
        private Response response = null;

        public ActionableFeatureTask() {
            dialog = new ProgressDialog( rlView.getContext() );
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage( "Communicating with pi, please wait..." );
            dialog.show();
        }

        @Override
        protected Void doInBackground( Void... params ) {
            try {
                response = myself.action();
            } catch ( KeyManagementException e ) {
                e.printStackTrace();
            } catch ( NoSuchAlgorithmException e ) {
                e.printStackTrace();
            } catch ( IOException e ) {
                e.printStackTrace();
            } catch ( JSONException e ) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute( final Void success ) {
            View v = getViewAfterAction( response );
            if ( v != null ) {
                rlView.removeAllViews();
                rlView.addView( v );
            }
            if ( dialog.isShowing() ) {
                dialog.dismiss();
            }
        }

        @Override
        protected void onCancelled() {
            if ( dialog.isShowing() ) {
                dialog.dismiss();
            }
        }

    }

}
