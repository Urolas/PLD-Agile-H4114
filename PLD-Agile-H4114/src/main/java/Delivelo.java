import controller.Controller;
import filecontrol.XMLException;
import org.xml.sax.SAXException;
import model.CityMap;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Delivelo {

    public static void main(String[] args) throws XMLException, ParserConfigurationException, IOException, SAXException {
        CityMap cityMap = new CityMap();
        new Controller(cityMap);
    }
}
