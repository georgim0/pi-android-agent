package com.kupepia.piandroidagent;

import android.content.Context;
import android.view.View;

public abstract class DisplayWidget extends Widget {

	private String content;
	
	public DisplayWidget(String content) {
		this.content = content;
	}

}
