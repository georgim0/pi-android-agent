package com.kupepia.piandroidagent;

import android.content.Context;
import android.view.View;

public class TextviewDisplayWidget extends DisplayWidget {

	private int heading;
	public TextviewDisplayWidget(String content) {
		super(content);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View createView(Context c) {
		// TODO Auto-generated method stub
		return null;
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
