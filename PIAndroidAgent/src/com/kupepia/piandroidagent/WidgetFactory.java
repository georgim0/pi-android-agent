package com.kupepia.piandroidagent;

import android.util.Log;

public class WidgetFactory {
	
	
	public static Widget createWidget(WidgetType type, String uri)
	{
		Widget widget = null;
		switch (type)
		{
			case TEXTVIEW:
			case TEXTVIEW_H1:
			case TEXTVIEW_H2:
			case TEXTVIEW_H3:
			case TEXTVIEW_H4:
			case TEXTVIEW_H5:
			case TEXTVIEW_H6:
				widget = new TextviewDisplayWidget(uri);
				int heading = type.ordinal() - WidgetType.TEXTVIEW.ordinal();
				((TextviewDisplayWidget)widget).setHeading(heading);
				break;
			case LISTVIEW:
				break;
			case CHECKBOX:
				widget = new CheckboxInputWidget("", uri);
				break;
			case DROPDOWNLIST:
				widget = new DropDownListInputWidget("", uri);
				break;
			case TABLE:
				break;
			case INPUTBOX:
				break;
			default:
				return null;
		}
		return widget;
		
	}
	
	public static Widget createWidgetWithLabel(WidgetType type, String uri, String labelContent)
	{
		Widget widget = null;
		widget = createWidget(type, uri);
		if (widget instanceof InputWidget)
			((InputWidget)widget).setLabelContent(labelContent);
		return widget;
		
	}
	
	public static Widget createWidget(String sType, String uri)
	{
		WidgetType type = WidgetType.fromString(sType);
		Log.e("createWidget", type.getText());
		return createWidget(type, uri);
		
	}
	
	public static Widget createWidgetWithLabel(String sType, String uri, String labelContent)
	{
		WidgetType type = WidgetType.fromString(sType);
		return createWidgetWithLabel(type, labelContent, uri);
		
	}

}
