package com.kupepia.piandroidagent;



public abstract class DisplayWidget extends Widget {

	private String content;
	
	public DisplayWidget(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}
}
