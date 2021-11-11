/**
 * CityMapState
 *
 * @author 4IF-4114
 */
package controller;

import org.xml.sax.SAXException;
import view.Window;
import filecontrol.XMLDeserializer;
import filecontrol.XMLException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * CityMapState state used when the city map is loaded
 */
public class CityMapState implements State {

    /**
     * Default constructor
     */
    public CityMapState() {
    }

    public void loadDistribution(Controller controller, Window window) throws XMLException, ParserConfigurationException, IOException, SAXException {
        XMLDeserializer.loadDistribution(controller.getCityMap());
        window.displayMessage("Distribution loaded.\nA tour can be computed.");
        controller.setCurrentState(controller.DISTRIBUTION_STATE);
    }

    public void loadMap(Controller controller, Window window) throws XMLException, ParserConfigurationException, IOException, SAXException {
        XMLDeserializer.loadCityMap(controller.getCityMap());
        window.getMapView().resetZoom();
        window.displayMessage("Please load a distribution.");
        controller.setCurrentState(controller.CITY_MAP_STATE);
    }


    public void enableButtons(Window window, ListOfCommands listOfCommands) {
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
