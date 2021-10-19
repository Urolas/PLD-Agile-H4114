
import controller.Controller;
import model.Distribution;
import org.xml.sax.SAXException;
import xml.*;
import model.CityMap;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class main {

    public static void main(String[] args) throws XMLException, ParserConfigurationException, IOException, SAXException {

        CityMap c=new CityMap();
        new Controller(c);
        XMLDeserializer.loadCityMap(c);
        XMLDeserializer.loadDistribution(c);
        System.out.println("Hello world");
    }

    public static String returnString(){
        return("ça marche");
    }
}
