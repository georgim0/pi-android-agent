package com.kupepia.piandroidagent.test;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import android.test.AndroidTestCase;

import com.kupepia.piandroidagent.CheckboxInputWidget;
import com.kupepia.piandroidagent.DropDownListInputWidget;
import com.kupepia.piandroidagent.R;
import com.kupepia.piandroidagent.TextviewDisplayWidget;
import com.kupepia.piandroidagent.Widget;
import com.kupepia.piandroidagent.XML2Widget;

public class XmlToWidgetTest extends AndroidTestCase {

	public void testCreateWidgetFromDoc() throws Throwable {

		InputStream xmlInputStream = this.mContext.getResources()
				.openRawResource(R.raw.sample1);

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlInputStream);

		XML2Widget widget = new XML2Widget(doc);

		ArrayList<Widget> widgets = widget.getWidgets();
		Widget textview = widgets.get(0);
		Widget checkbox = widgets.get(1);
		Widget dropdown = widgets.get(2);
		
		assertTrue(textview instanceof TextviewDisplayWidget);
		assertTrue(dropdown instanceof DropDownListInputWidget);
		assertTrue(checkbox instanceof CheckboxInputWidget);
	}

}
