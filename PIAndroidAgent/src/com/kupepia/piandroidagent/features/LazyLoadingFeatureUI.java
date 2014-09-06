package com.kupepia.piandroidagent.features;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.json.JSONException;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.RelativeLayout;

import com.kupepia.piandroidagent.requests.CommunicationManager;
import com.kupepia.piandroidagent.requests.Response;

public abstract class LazyLoadingFeatureUI extends FeatureUI {
    private String query;
    private final LazyLoadingFeatureUI myself;

    public LazyLoadingFeatureUI() {
        myself = this;
    }

    private void setNewQuery( String query ) {
        this.query = query;
    }

    public void applyAction( String query ) {
        setNewQuery( query );
        LazyLoadFeatureTask aft = new LazyLoadFeatureTask();
        aft.execute();
    }

    private Response action() throws KeyManagementException,
            NoSuchAlgorithmException, IOException, JSONException {

        CommunicationManager cm = CommunicationManager.getInstance();
        Response r = cm.sendRequest( query );
        return r;
    }

    public abstract View getViewAfterAction( Response r );

    public class LazyLoadFeatureTask extends AsyncTask<Void, Void, Void> {

        private ProgressDialog dialog;
        private Response response = null;

        public LazyLoadFeatureTask() {
            dialog = new ProgressDialog( rlView.getContext() );
        }

        @Override
        protected void onPreExecute() {
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
            getViewAfterAction( response );
            
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
