package com.kupepia.piandroidagent;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XML2Widget {

	private ArrayList<Widget> widgets;

	public XML2Widget(Document doc) {

		widgets = new ArrayList<Widget>();
		try {
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("widget");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					String labelContent = eElement
							.getElementsByTagName("label").item(0).getTextContent();
					String uri = eElement.getElementsByTagName("uri").item(0).getTextContent();

					String attributeType = eElement.getAttribute("type");
					String attributeHeading = eElement.getAttribute("heading");
					if (attributeHeading != null){
						attributeType.concat("_h" + attributeHeading);
					}
					Widget widget = null;
					if (labelContent.length() > 0)
						widget = WidgetFactory.createWidgetWithLabel(
								attributeType, uri, labelContent);
					else
						widget = WidgetFactory.createWidget(attributeType, uri);
					widgets.add(widget);

				}// if
			}

			NodeList nList2 = doc.getElementsByTagName("submit");
			Node nNode = nList2.item(0);

			// System.out.println("\nCurrent Element :" + nNode.getNodeName());
			Element eElement = (Element) nNode;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ArrayList<Widget> getWidgets() {
		return widgets;
	}

}
