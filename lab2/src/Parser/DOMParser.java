package Parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


public class DOMParser implements ParserXML {
    private  XMLHandler handler;

    DOMParser(XMLHandler handler){
        this.handler = handler;
        
    }

    @Override
    public void parse(String xmlPath) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document doc = null;
        try {
            builder = factory.newDocumentBuilder();
            doc = builder.parse(xmlPath);
        } catch (ParserConfigurationException | SAXException | IOException e) {}

        Element root = doc.getDocumentElement();

        NodeList knifeNodes = root.getElementsByTagName(handler.name);

        for (int i = 0; i < knifeNodes.getLength(); i++) {

            Element knifeElem = (Element) knifeNodes.item(i);
            NodeList childNodes = knifeElem.getChildNodes();

            for (int j = 0; j < childNodes.getLength(); j++) {
                if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {

                    Element child = (Element) childNodes.item(j);

                    handler.setTag(child.getNodeName(), getChildValue(knifeElem, child.getNodeName()));
                    NodeList childChildNodes = child.getChildNodes();

                    for (int k = 0; k < childChildNodes.getLength(); k++) {
                        if (childChildNodes.item(k).getNodeType() == Node.ELEMENT_NODE) {
                            Element childChild = (Element) childChildNodes.item(k);
                            handler.setTag(childChild.getNodeName(), getChildValue(child, childChild.getNodeName()));
                        }
                    }
                }
            }
            handler.endTag(knifeElem.getNodeName());
        }
    }


    private static String getChildValue(Element element, String name) {
        Element child = (Element) element.getElementsByTagName(name).item(0);
        if (child == null) {
            return "";
        }
        Node node = child.getFirstChild();
        return node.getNodeValue();
    }

}
