package com.kupepia.piandroidagent.test;

import android.test.AndroidTestCase;

import com.kupepia.piandroidagent.CheckboxInputWidget;
import com.kupepia.piandroidagent.DropDownListInputWidget;
import com.kupepia.piandroidagent.TextviewDisplayWidget;
import com.kupepia.piandroidagent.Widget;
import com.kupepia.piandroidagent.WidgetFactory;
public class TestWidgetFactory extends AndroidTestCase {

	
    public void testCreateWidgetFromString() throws Throwable {
       Widget widget_textview = 
    		   WidgetFactory.createWidget("textview", "This is some text");
       Widget widget_dropdownlist = 
    		   WidgetFactory.createWidget("dropdownlist", "uri://");
       Widget widget_checkbox = 
    		   WidgetFactory.createWidget("checkbox", "uri://");
       
       assertTrue(widget_textview instanceof TextviewDisplayWidget);
       assertTrue(widget_dropdownlist instanceof DropDownListInputWidget);
       assertTrue(widget_checkbox instanceof CheckboxInputWidget);
    }
    
    public void testCreateTextviewWidgetWithHeadings() throws Throwable {
    	Widget widget_textviewplain = 
    			WidgetFactory.createWidget("textview", "This is some text");
    	Widget widget_textviewh1 = 
    			WidgetFactory.createWidget("textview_h1", "");
    	Widget widget_textviewh6 = 
    			WidgetFactory.createWidget("textview_h6", "");
    	
    	int h0 = ((TextviewDisplayWidget)widget_textviewplain).getHeading();
    	int h1 = ((TextviewDisplayWidget)widget_textviewh1).getHeading();
    	int h6 = ((TextviewDisplayWidget)widget_textviewh6).getHeading();
    	
    	assertTrue(h0==0);
    	assertTrue(h1==1);
    	assertTrue(h6==6);
    	
    }
}

