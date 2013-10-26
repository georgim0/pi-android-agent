package com.kupepia.piandroidagent;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XML2Menu {

	private static XML2Menu xml2Menu = null;
	private ArrayList<MenuElement> menuList = null;

	private XML2Menu(Document doc) {
		this.menuList = new ArrayList<MenuElement>();
		parse(doc);
	}

	public static XML2Menu getInstance(Document doc) {

		if (xml2Menu == null && doc != null) {
			xml2Menu = new XML2Menu(doc);
		}
		return xml2Menu;
	}

	public ArrayList<MenuElement> getMenuList() {
		return this.menuList;
	}

	public void parse(Document doc) {
		NodeList nList = doc.getElementsByTagName("action");

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;
				String labelContent = eElement.getElementsByTagName("label")
						.item(0).getTextContent();
				String uri = eElement.getElementsByTagName("uri").item(0)
						.getTextContent();
				String value = eElement.getElementsByTagName("value").item(0)
						.getTextContent();
				String url = eElement.getElementsByTagName("url").item(0)
						.getTextContent();
				this.menuList.add(new MenuElement(uri, labelContent, value, url));

			}// if

		}
	}

	public MenuElement getSelected(String label) {
		for (MenuElement menuElement : this.menuList) {
			if (menuElement.getLabel() == label) {
				return menuElement;
			}
		}
		return null;
	}
}
