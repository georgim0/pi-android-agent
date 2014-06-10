package com.kupepia.piandroidagent.ui;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kupepia.piandroidagent.R;
import com.kupepia.piandroidagent.dummy.AppContent;
import com.kupepia.piandroidagent.features.FeatureUI;

/**
 * A fragment representing a single Activity detail screen. This fragment is
 * either contained in a {@link ActivityListActivity} in two-pane mode (on
 * tablets) or a {@link ActivityDetailActivity} on handsets.
 */
public class ActivityDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String FEATURE_ID = "feature_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private FeatureUI mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ActivityDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(FEATURE_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = AppContent.ITEMS.get(getArguments().getInt(
                    FEATURE_ID));
            
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        
        View rootView = inflater.inflate(R.layout.fragment_activity_detail,
                container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            RelativeLayout rl_view = ((RelativeLayout) rootView.findViewById(R.id.activity_detail_rl));
            rl_view.removeAllViews();
            try {
                mItem.activate(rl_view);
            } catch (Exception e) {
                Toast.makeText(container.getContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        }
        this.getActivity().setTitle(mItem.getID());
        
        return rootView;
    }
}
