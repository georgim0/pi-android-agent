package com.kupepia.piandroidagent;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;

public class CheckboxInputWidget extends InputWidget {

	public CheckboxInputWidget(String label, String uri) {
		super(label, uri);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View createMe(Context c) {
		// TODO Auto-generated method stub
        CheckBox cb = new CheckBox(c);
		return cb;
	}

}
