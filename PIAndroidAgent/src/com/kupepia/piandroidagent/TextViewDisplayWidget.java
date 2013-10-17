package com.kupepia.piandroidagent;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class TextViewDisplayWidget extends DisplayWidget {
	
	public TextViewDisplayWidget(String content) {
		super(content);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View createView(Context c) {
		// TODO Auto-generated method stub
		
		TextView tv = new TextView(c);
		tv.setText(this.getContent());
		return tv;
	}

}
