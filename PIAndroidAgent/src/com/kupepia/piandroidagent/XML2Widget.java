package com.kupepia.piandroidagent;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.kupepia.piandroidagent.requests.RequestFormat;

public class XML2Widget {

	public static WidgetForm XML2WidgetForm(Document doc) {
		WidgetForm widgetForm = null;
		ArrayList<Widget> widgets = new ArrayList<Widget>();

		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("widget");

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;
				String labelContent = eElement
						.getElementsByTagName("label").item(0)
						.getTextContent();
				String uri = eElement.getElementsByTagName("uri").item(0)
						.getTextContent();

				String attributeType = eElement.getAttribute("type");
				String attributeHeading = eElement.getAttribute("heading");
				if (attributeHeading != null && attributeHeading.length() > 0) {
					attributeType = attributeType.concat("_h" + attributeHeading);
				}
				Widget widget = null;
				if (labelContent.length() > 0)
					widget = WidgetFactory.createWidgetWithLabel(
							attributeType, uri, labelContent);
				else
					widget = WidgetFactory.createWidget(attributeType, uri);
					
				if (widget instanceof DropDownListInputWidget)
				{
					Node entriesNode = eElement.getElementsByTagName("entries").item(0);
					NodeList entriesNodeList = entriesNode.getChildNodes();
					for (int i = 0; i < entriesNodeList.getLength(); i++)
					{
						Node node = entriesNodeList.item(i);
						((DropDownListInputWidget)widget).addEntry(node.getTextContent());
					}
				}
				widgets.add(widget);

			}// if
		}

		NodeList nList2 = doc.getElementsByTagName("submit");
		SubmitButtonWidget sbw = null;
		if (nList2 != null) {
			Node nNode = nList2.item(0);
			Element submitElement = (Element) nNode;
			String submitUrl = submitElement.getElementsByTagName("url")
					.item(0).getTextContent();
			NodeList labelList = submitElement.getElementsByTagName("label");
			String label = null;
			if (labelList == null || labelList.getLength() == 0)
				label = "Submit";
			else
				label = labelList.item(0).getTextContent();
			if (label.length() == 0)
				label = "Submit";
			Element formatElement = 
					(Element) submitElement.getElementsByTagName("format")
					.item(0);
			
			String formatURL = formatElement.getElementsByTagName("url").item(0)
					.getTextContent();
			
			RequestFormat rf = new RequestFormat(formatURL);
			sbw = new SubmitButtonWidget(submitUrl, label);
			sbw.setRequestFormat(rf);
		}
		widgetForm = new WidgetForm(widgets, sbw);
		return widgetForm;
		

	}


}
