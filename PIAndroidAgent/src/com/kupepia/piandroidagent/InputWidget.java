package com.kupepia.piandroidagent;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public  abstract class InputWidget extends Widget {

	private final String labelContent;
	private final String uri;
	
	public InputWidget(String label, String uri)
	{
		this.labelContent = label;
		this.uri = uri;
	}
	
	private View createLabel(Context c)
	{
		TextView label = new TextView(c);
		label.setText(labelContent);
		return label;
	}
	

	public View createView(Context c)
	{
		RelativeLayout rl = new RelativeLayout(c);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		View label = createLabel(c);
		label.setId(label.hashCode());
		View inputWidget = this.createMe(c);
		inputWidget.setId(inputWidget.hashCode());
		rl.addView(label, lp);
		lp = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
		lp.addRule(RelativeLayout.RIGHT_OF, label.getId());
		rl.addView(inputWidget, lp);
		return rl;
		
	}
	public String getUri() {
		return uri;
	}
	
	
	

}
