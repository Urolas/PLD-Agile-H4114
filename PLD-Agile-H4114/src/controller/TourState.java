/**
 * TourState
 *
 * @author 4IF-4114
 */
package controller;


import model.CityMap;
import model.DepotAddress;
import model.Intersection;
import model.PointOfInterest;
import org.xml.sax.SAXException;
import view.Window;
import filecontrol.RoadMapGenerator;
import filecontrol.XMLDeserializer;
import filecontrol.XMLException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * TourState state used when the tour is loaded
 */
public class TourState implements State {

    /**
     * Default constructor
     */
    public TourState() {
    }

    @Override
    public void loadMap(Controller controller, Window window) throws XMLException, ParserConfigurationException,
            IOException, SAXException {
        XMLDeserializer.loadCityMap(controller.getCityMap());
        window.getMapView().resetZoom();
        window.displayMessage("Please load a distribution.");
        controller.setCurrentState(controller.CITY_MAP_STATE);
        controller.resetListOfCommands();
    }

    @Override
    public void loadDistribution(Controller controller, Window window) throws XMLException,
            ParserConfigurationException, IOException, SAXException {
        XMLDeserializer.loadDistribution(controller.getCityMap());
        window.displayMessage("Distribution loaded.\nA tour can be computed.");
        controller.setCurrentState(controller.DISTRIBUTION_STATE);
        controller.resetListOfCommands();

    }

    @Override
    public void addRequest(Controller c) {
        c.ADD_STATE_1.entryAction(c.getWindow());
        c.setCurrentState(c.ADD_STATE_1);
        c.resetListOfCommands();

    }

    @Override
    public void up(Integer id, ListOfCommands listOfCommands, Controller controller) {
        try {
            listOfCommands.add(new SwapCommand(controller.getCityMap(), id, -1));
        } catch (Exception e) {
            controller.getWindow().parsingError(e.getMessage());
        }

    }

    @Override
    public void down(Integer id, ListOfCommands listOfCommands, Controller controller) {
        try {
            listOfCommands.add(new SwapCommand(controller.getCityMap(), id, 1));
        } catch (Exception e) {
            controller.getWindow().parsingError(e.getMessage());
        }
    }

    @Override
    public void leftClick(Controller controller, Window window, CityMap cityMap, ListOfCommands listOfCommands,
                          Intersection intersection, PointOfInterest poi) {
        if (poi != null && !(poi instanceof DepotAddress)) {
            controller.HIGHLIGHT_STATE.entryAction(poi, cityMap);
            controller.setCurrentState(controller.HIGHLIGHT_STATE);
        }
    }

    @Override
    public void undo(ListOfCommands listOfCommands) {
        listOfCommands.undo();
    }

    @Override
    public void redo(ListOfCommands listOfCommands) {
        listOfCommands.redo();
    }

    @Override
    public void generateRoadmap(Controller controller, Window window) {
        RoadMapGenerator.generateRoadmap(controller.getCityMap());
        controller.setCurrentState(controller.TOUR_STATE);
    }

    public void enableButtons(Window window, ListOfCommands listOfCommands) {
        window.enableButton("Load a city map", true);
        window.enableButton("Load a distribution", true);
        window.enableButton("Compute a tour", false);
        window.enableButton("Add request", true);
        window.enableButton("Remove", false);
        window.enableButton("Generate roadmap", true);
        window.enableButton("Undo", listOfCommands.getCurrentIndex() >= 0);
        window.enableButton("Redo", listOfCommands.getCurrentIndex() < listOfCommands.getList().size() - 1);
    }
}
