package com.kupepia.piandroidagent;



public abstract class DisplayWidget extends Widget {

	private String content;
	private String uri;
	
	public DisplayWidget(String content, String uri) {
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}
	
	public String getUri()
	{
		return this.uri;
	}
}
