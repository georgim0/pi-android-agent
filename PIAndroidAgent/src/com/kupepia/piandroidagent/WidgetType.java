package com.kupepia.piandroidagent;

public enum WidgetType {
	LISTVIEW("listview"), CHECKBOX("checkbox"), TABLE("table"), 
	DROPDOWNLIST("droplist"), INPUTBOX("inputbox"), 
	TEXTVIEW("textview"), TEXTVIEW_H1("textview_h1"), 
	TEXTVIEW_H2("textview_h2"), TEXTVIEW_H3("textview_h3"), 
	TEXTVIEW_H4("textview_h4"), TEXTVIEW_H5("textview_h5"), 
	TEXTVIEW_H6("textview_h6");
	
	private String text;

	  WidgetType(String text) {
	    this.text = text;
	  }

	  public String getText() {
	    return this.text;
	  }

	  public static WidgetType fromString(String text) {
	    if (text != null) {
	      for (WidgetType b : WidgetType.values()) {
	        if (text.equalsIgnoreCase(b.text)) {
	          return b;
	        }
	      }
	    }
	    return null;
	  }
}
