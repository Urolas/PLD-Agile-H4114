import model.Distribution;
import org.xml.sax.SAXException;
import xml.*;
import model.CityMap;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class main {

    public static void main(String[] args) throws XMLException, ParserConfigurationException, IOException, SAXException {
        xml.XMLDeserializer alo = new XMLDeserializer();
        CityMap c=new CityMap();
        alo.loadCityMap(c);
        Distribution d = new Distribution();
        alo.loadDistribution(d,c);
        System.out.println("Hello world");
    }

    public static String returnString(){
        return("ça marche");
    }
}