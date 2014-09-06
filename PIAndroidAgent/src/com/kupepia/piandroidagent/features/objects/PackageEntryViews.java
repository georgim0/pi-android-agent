package com.kupepia.piandroidagent.features.objects;

import android.widget.Switch;
import android.widget.TextView;

public class PackageEntryViews {
    private TextView tvPackName = null;
    private TextView tvDescription = null;
    private Switch switchPackStatus = null;

    public PackageEntryViews() {

    }

    public TextView getTvPackName() {
        return tvPackName;
    }

    public void setTvPackName( TextView tvPackName ) {
        this.tvPackName = tvPackName;
    }

    public TextView getTvDescription() {
        return tvDescription;
    }

    public void setTvDescription( TextView tvDescription ) {
        this.tvDescription = tvDescription;
    }

    public Switch getSwitchPackStatus() {
        return switchPackStatus;
    }

    public void setSwitchPackStatus( Switch switchPackStatus ) {
        this.switchPackStatus = switchPackStatus;
    }

}
