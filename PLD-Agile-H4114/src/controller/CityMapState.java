package controller;

import org.xml.sax.SAXException;
import view.Window;
import xml.XMLDeserializer;
import xml.XMLException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * @author 4IF-4114
 */
public class CityMapState implements State {

    /**
     * Default constructor
     */
    public CityMapState() {
    }

    public void loadDistribution(Controller c, Window w) throws XMLException, ParserConfigurationException, IOException, SAXException {
        XMLDeserializer.loadDistribution(c.getCitymap());
        c.setCurrentState(c.distributionState);
    }
    public void loadMap(Controller c, Window w) throws XMLException, ParserConfigurationException, IOException, SAXException {
        XMLDeserializer.loadCityMap(c.getCitymap());
        c.setCurrentState(c.citymapState);
    }

    public void zoomIn(Controller c, Window w){
        //TODO
        w.getMapView().modifyZoom(1.5);
    }
    public void zoomOut(Controller c, Window w){
        //TODO
        w.getMapView().modifyZoom(1/1.5);
    }
    public void recenter(Controller c, Window w){
        //TODO
        w.getMapView().modifyZoom(1);

    }


}