package com.kupepia.piandroidagent.features;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.RelativeLayout;

public abstract class FeatureUI implements Feature {

    private final FeatureUI myself;
    private RelativeLayout rlView = null;

    public FeatureUI() {
        myself = this;
    }

    public void activate( RelativeLayout rl_view ) {
        rlView = rl_view;
        FeatureBackgroundTask mAuthTask = new FeatureBackgroundTask();
        mAuthTask.execute( (Void) null );
    }
    
    public String toString() {
        return this.getID();
    }

    public class FeatureBackgroundTask extends AsyncTask<Void, Void, Void> {
        
        private ProgressDialog dialog;
        
        public FeatureBackgroundTask() {
            dialog = new ProgressDialog(rlView.getContext());
        }
            
        @Override
        protected void onPreExecute() {
            dialog.setMessage("Communicating with pi, please wait...");
            dialog.show();
        }
        
        @Override
        protected Void doInBackground( Void... params ) {

            try {
                myself.init();
            } catch ( Exception e ) {

            }

            return null;

        }

        @Override
        protected void onPostExecute( final Void success ) {
            rlView.addView( myself.getView( rlView.getContext() ) );
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

        @Override
        protected void onCancelled() {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
        
    }
}
