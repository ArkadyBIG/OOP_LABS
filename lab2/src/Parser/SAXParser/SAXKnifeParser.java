package Parser.SAXParser;

import Parser.ParserXML;
import Parser.XMLHandler;
import Knife.Knife;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class SAXKnifeParser implements ParserXML{
    private XMLHandler handler;
    public SAXKnifeParser(XMLHandler handler){
        this.handler = handler;
    }

    @Override
    public void parse(String xmlPath) throws IllegalArgumentException {

        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            SAXHandler saxHandler = new SAXHandler(handler);
            saxParser.parse(new File(xmlPath), saxHandler);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Error: " + e.getMessage());
        }
    }
}
