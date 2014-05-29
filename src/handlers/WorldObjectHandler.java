package handlers;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import entities.XMLObject;

import java.io.File;


public class WorldObjectHandler {

	public static List<XMLObject> objectList =	Collections.synchronizedList(
													new ArrayList<XMLObject>(16));

	// loads all world objects in a specified level
	public static void loadObjects(String level) {
		try {

			File fXmlFile = new File("res/xml/" + level + ".xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("object");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					int id = Integer.valueOf(eElement.getAttribute("id"));
					String name = eElement.getElementsByTagName("name").item(0).getTextContent();
					int x = Integer.valueOf(eElement.getElementsByTagName("x").item(0).getTextContent());
					int y = Integer.valueOf(eElement.getElementsByTagName("y").item(0).getTextContent());
					int width = Integer.valueOf(eElement.getElementsByTagName("width").item(0).getTextContent());
					int height = Integer.valueOf(eElement.getElementsByTagName("height").item(0).getTextContent());
					String textureName = eElement.getElementsByTagName("texture").item(0).getTextContent();
					
					XMLObject obj = new XMLObject(id, name, x, y, width, height, textureName);
					objectList.add(obj);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
	public static void setObjectList(List<XMLObject> newObjectList) {
		objectList = newObjectList;
	}

	public static List<XMLObject> getObjectList() {
		return objectList;
	}
}
