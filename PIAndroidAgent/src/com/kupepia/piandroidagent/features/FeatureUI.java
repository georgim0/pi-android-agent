package com.kupepia.piandroidagent.features;

import com.kupepia.piandroidagent.R;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

public abstract class FeatureUI implements Feature {

    private final FeatureUI myself;
    protected RelativeLayout rlView = null;
    
    public FeatureUI() {
        myself = this;
    }

    protected FeatureUI getMe() {
        return myself;
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
        private boolean error = false;

        public FeatureBackgroundTask() {
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
                myself.init();
            } catch ( Exception e ) {
                error = true;
            }

            return null;

        }

        @Override
        protected void onPostExecute( final Void success ) {
            rlView.removeAllViews();
            if ( !error ) {
                rlView.addView( myself.getView( rlView.getContext() ),
                        new RelativeLayout.LayoutParams(
                                LayoutParams.MATCH_PARENT,
                                LayoutParams.MATCH_PARENT ) );
                if ( dialog.isShowing() ) {
                    dialog.dismiss();
                }
            } else {
                TextView tv = new TextView( rlView.getContext() );
                tv.setText( rlView.getContext().getResources()
                        .getString( R.string.init_error ) );
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
