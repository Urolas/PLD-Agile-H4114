
import controller.Controller;
import org.xml.sax.SAXException;
import filecontrol.*;
import model.CityMap;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class main {

    public static void main(String[] args) throws XMLException, ParserConfigurationException, IOException, SAXException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        CityMap c=new CityMap();
        new Controller(c);
    }

    public static String returnString(){
        return("ça marche");
    }
}
