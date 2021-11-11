package controller;

import org.xml.sax.SAXException;
import view.Window;
import filecontrol.XMLDeserializer;
import filecontrol.XMLException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


/**
 * @author 4IF-4114
 */
public class InitialState implements State {

    /**
     * Default constructor
     */
    public InitialState() {
    }

    public void loadMap(Controller controller, Window window) throws XMLException, ParserConfigurationException,
            IOException, SAXException {
        XMLDeserializer.loadCityMap(controller.getCityMap());
        window.getMapView().resetZoom();
        window.displayMessage("Please load a distribution.");
        controller.setCurrentState(controller.CITY_MAP_STATE);
    }

    public void enableButtons(Window window, ListOfCommands listOfCommands) {
        window.enableButton("Load a city map", true);
        window.enableButton("Load a distribution", false);
        window.enableButton("Compute a tour", false);
        window.enableButton("Add request", false);
        window.enableButton("Remove", false);
        window.enableButton("Redo", false);
        window.enableButton("Undo", false);
        window.enableButton("Generate roadmap", false);
    }
}
