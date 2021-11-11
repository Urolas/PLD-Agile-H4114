package controller;

import org.xml.sax.SAXException;
import view.Window;
import view.MapView;
import filecontrol.XMLDeserializer;
import filecontrol.XMLException;

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
        w.displayMessage("Distribution loaded.\nA tour can be computed.");
        c.setCurrentState(c.distributionState);
    }
    public void loadMap(Controller c, Window w) throws XMLException, ParserConfigurationException, IOException, SAXException {
        XMLDeserializer.loadCityMap(c.getCitymap());
        w.getMapView().resetZoom();
        w.displayMessage("Please load a distribution.");
        c.setCurrentState(c.citymapState);
    }


    public  void enableButtons(Window window, ListOfCommands loc) {
        window.enableButton("Load a city map", true);
        window.enableButton("Load a distribution", true);
        window.enableButton("Compute a tour", false);
        window.enableButton("Add request", false);
        window.enableButton("Remove", false);
        window.enableButton("Redo", false);
        window.enableButton("Undo", false);
        window.enableButton("Generate roadmap", false);
    }


}
