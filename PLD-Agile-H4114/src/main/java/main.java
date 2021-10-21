
import controller.Controller;
import model.Distribution;
import model.Intersection;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import xml.*;
import model.CityMap;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class main {

    public static void main(String[] args) throws XMLException, ParserConfigurationException, IOException, SAXException {
        CityMap c=new CityMap();
        new Controller(c);
        XMLDeserializer.loadCityMap(c);
        XMLDeserializer.loadDistribution(c);
        c.computeTour();
        System.out.println("Hello");



    }

    public static String returnString(){
        return("ça marche");
    }
}
