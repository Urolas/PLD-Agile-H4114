package controller;
import org.xml.sax.SAXException;
import view.Window;
import model.*;
import xml.XMLDeserializer;
import xml.XMLException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

/**
 * @author 4IF-4114
 */
public class State {

    /**
     * Default constructor
     */
    public State() {
    }

    /**
     * Method called by the controller after a click on the button "Load a map"
     * @param c the controler
     * @param w the window
     */
    public void loadMap(Controller c, Window w) throws XMLException, ParserConfigurationException, IOException, SAXException {
//        XMLDeserializer.loadCityMap(c.getCitymap());
    }

    /**
     * Method called by the controller after a click on the button "Load distribution"
     * @param c the controler
     * @param w the window
     */
    public void loadDistribution(Controller c, Window w) throws XMLException, ParserConfigurationException, IOException, SAXException{
//        XMLDeserializer.loadDistribution(c.getCitymap().getDistribution(),c.getCitymap());
    }

}