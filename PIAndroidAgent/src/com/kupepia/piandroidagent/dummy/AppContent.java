package com.kupepia.piandroidagent.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.kupepia.piandroidagent.features.FeatureUI;
import com.kupepia.piandroidagent.features.Firewall;
import com.kupepia.piandroidagent.features.GPIO;
import com.kupepia.piandroidagent.features.Overview;
import com.kupepia.piandroidagent.features.Services;
import com.kupepia.piandroidagent.features.Updates;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class AppContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<FeatureUI> ITEMS = new ArrayList<FeatureUI>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, FeatureUI> ITEM_MAP =
            new HashMap<String, FeatureUI>();

    static {
        // Add 3 sample items.
        addItem( new Overview() );
        addItem( new Services() );
        addItem( new Updates() );
        addItem( new Firewall() );
        addItem( new GPIO() );
    }

    private static void addItem( FeatureUI item ) {
        ITEMS.add( item );
        ITEM_MAP.put( item.getID(), item );
    }

}
