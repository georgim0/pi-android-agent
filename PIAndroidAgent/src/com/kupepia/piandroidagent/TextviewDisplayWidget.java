package com.kupepia.piandroidagent;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class TextviewDisplayWidget extends DisplayWidget {

	private int heading;
	
	public TextviewDisplayWidget(String content, String uri) {
		super(content, uri);
	}


	@Override
	public View createView(Context c) {
		TextView tv = new TextView(c);
		tv.setText(this.getContent());
		//heading needs adding
		return tv;
	}

	public void setHeading(int heading)
	{
		this.heading = heading;
	}
	public int getHeading()
	{
		return this.heading;
	}
	
}
