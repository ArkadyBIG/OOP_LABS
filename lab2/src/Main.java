import Parser.KnifeParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, XMLStreamException {

        KnifeParser knifeParser = new KnifeParser();

        System.out.println("SAX parser");
        knifeParser.parse("src/resources/Knife.xml",
                          "src/resources/Knife.xsd", "sax");

        System.out.println("\nStAX parser");
        knifeParser.parse("src/resources/Knife.xml",
                          "src/resources/Knife.xsd", "stax");

        System.out.println("\nDOM parser");
        knifeParser.parse("src/resources/Knife.xml",
                          "src/resources/Knife.xsd", "dom");
    }
}
