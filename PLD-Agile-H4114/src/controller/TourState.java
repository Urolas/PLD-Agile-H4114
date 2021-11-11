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
 * @author 4IF-4114
 */
public class TourState implements State {

    /**
     * Default constructor
     */
    public TourState() {
    }

    @Override
    public void loadMap(Controller c, Window w) throws XMLException, ParserConfigurationException, IOException, SAXException {
        XMLDeserializer.loadCityMap(c.getCityMap());
        w.getMapView().resetZoom();
        w.displayMessage("Please load a distribution.");
        c.setCurrentState(c.CITY_MAP_STATE);
        c.resetListOfCommands();
    }

    @Override
    public void loadDistribution(Controller c, Window w) throws XMLException, ParserConfigurationException, IOException, SAXException {
        XMLDeserializer.loadDistribution(c.getCityMap());
        w.displayMessage("Distribution loaded.\nA tour can be computed.");
        c.setCurrentState(c.DISTRIBUTION_STATE);
        c.resetListOfCommands();

    }

    @Override
    public void modifyDistribution(Controller c) {
        c.ADD_STATE_1.entryAction(c.getWindow());
        c.setCurrentState(c.ADD_STATE_1);
        c.resetListOfCommands();

    }

    @Override
    public void up(Integer id, ListOfCommands listOfCommands, Controller c) {
        try {
            listOfCommands.add(new SwapCommand(c.getCityMap(), id, -1));
        } catch (Exception e) {
            c.getWindow().parsingError(e.getMessage());
        }

    }

    @Override
    public void down(Integer id, ListOfCommands listOfCommands, Controller c) {
        try {
            listOfCommands.add(new SwapCommand(c.getCityMap(), id, 1));
        } catch (Exception e) {
            c.getWindow().parsingError(e.getMessage());

        }

    }


    @Override
    public void leftClick(Controller c, Window w, CityMap cityMap, ListOfCommands l, Intersection i, PointOfInterest poi) {
        if (poi != null && !(poi instanceof DepotAddress)) {


            c.HIGHLIGHT_STATE.entryAction(poi, cityMap);

            c.setCurrentState(c.HIGHLIGHT_STATE);
        }
    }

    @Override
    public void undo(ListOfCommands listOfCdes) {
        listOfCdes.undo();
    }

    @Override
    public void redo(ListOfCommands listOfCdes) {
        listOfCdes.redo();
    }

    @Override
    public void generateRoadmap(Controller c, Window w) throws IOException {
        RoadMapGenerator.generateRoadmap(c.getCityMap());
        c.setCurrentState(c.TOUR_STATE);
    }

    public void enableButtons(Window window, ListOfCommands loc) {
        window.enableButton("Load a city map", true);
        window.enableButton("Load a distribution", true);
        window.enableButton("Compute a tour", false);
        window.enableButton("Add request", true);
        window.enableButton("Remove", false);
        window.enableButton("Generate roadmap", true);
        if (loc.getCurrentIndex() >= 0) {
            window.enableButton("Undo", true);
        } else {
            window.enableButton("Undo", false);
        }
        if (loc.getCurrentIndex() < loc.getList().size() - 1) {
            window.enableButton("Redo", true);
        } else {
            window.enableButton("Redo", false);
        }
    }
}
