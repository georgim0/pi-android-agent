package com.kupepia.piandroidagent;

import android.content.Context;
import android.text.Html;
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
		tv.setText(Html.fromHtml(this.getContent()));
		//heading needs adding
		switch (heading){
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				break;
			default:
				break;
		}
		
		tv.setTextSize(10);
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
