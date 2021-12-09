package Parser;

import Knife.KnifeComparator;
import Knife.Knifes;
import Parser.SAXParser.SAXKnifeParser;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public class KnifeParser {
    private ParserXML parser;
    private Knifes result;

    public Knifes parse(String xmlPath, String xsdPath, String parserName) throws ParserConfigurationException, SAXException, IOException, XMLStreamException {

        if(XMLValidator.validateXML(xmlPath, xsdPath)) {

            XMLHandler handler = new XMLHandler();

            switch (parserName.toUpperCase()) {
                case "SAX" : parser = new SAXKnifeParser(handler);
                case "DOM" : parser = new DOMParser(handler);
                case "STAX" : parser = new StAXParser(handler);
                default : {}
            }

            parser.parse(xmlPath);
            result = handler.getKnifes();
            result.sort(new KnifeComparator());
            System.out.println(result);
        }
        return result;
    }
}
