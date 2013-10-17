package com.kupepia.piandroidagent;



public abstract class DisplayWidget extends Widget {

	private final String content;
	
	public DisplayWidget(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}


}
