package com.kupepia.piandroidagent;

import com.kupepia.piandroidagent.requests.RequestFormat;

import android.content.Context;
import android.widget.Button;

public class SubmitButtonWidget extends Widget {

	private RequestFormat requestFormat;
	private final String url;
	private final String label;
	public SubmitButtonWidget(String url, String label)
	{
		this.url = url;
		this.label = label;
	}
	
	@Override
	public Button createView(Context c) {
		Button button = new Button(c);
		button.setText(label);
		return button;
	}

	public RequestFormat getRequestFormat() {
		return requestFormat;
	}

	public void setRequestFormat(RequestFormat requestFormat) {
		this.requestFormat = requestFormat;
	}

	public String getUrl() {
		return url;
	}

}
