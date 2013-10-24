package com.kupepia.piandroidagent;

public class MenuElement {
	private String uri;
	private String label;
	private String value;

	public MenuElement(String uriIn, String labelIn, String valueIn) {
		this.uri = uriIn;
		this.label = labelIn;
		this.value = valueIn;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public void submit(){
		
	}

	public String getValue() {
		return this.value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
