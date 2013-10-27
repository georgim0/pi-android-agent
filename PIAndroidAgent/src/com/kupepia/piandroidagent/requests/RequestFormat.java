package com.kupepia.piandroidagent.requests;

import org.w3c.dom.Document;

public class RequestFormat {
	
	private final String url;
	private Document requestTemplate;

	public RequestFormat(String url)
	{
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public Document getRequestTemplate() {
		return requestTemplate;
	}

	public void setRequestTemplate(Document requestTemplate) {
		this.requestTemplate = requestTemplate;
	}
	
	
}
