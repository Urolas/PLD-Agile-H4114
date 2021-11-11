package controller;

import filecontrol.RoadMapGenerator;
import filecontrol.XMLDeserializer;
import filecontrol.XMLException;
import model.*;
import org.xml.sax.SAXException;
import view.Window;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class HighlightState implements State {
    private PointOfInterest highlightpoint;

    @Override
    public void removePointOfInterest(Controller controller, Window window, CityMap map,
                                      ListOfCommands listOfCommands) {
        try {
            listOfCommands.add(new DeleteCommand(map, this.highlightpoint));
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.setHighlighted(null, null);
        controller.setCurrentState(controller.TOUR_STATE);
    }

    @Override
    public void loadMap(Controller controller, Window window) throws XMLException, ParserConfigurationException,
            IOException, SAXException {
        controller.getCityMap().setHighlighted(null, null);
        XMLDeserializer.loadCityMap(controller.getCityMap());
        controller.setCurrentState(controller.CITY_MAP_STATE);
        controller.resetListOfCommands();
    }

    @Override
    public void loadDistribution(Controller controller, Window window) throws XMLException,
            ParserConfigurationException, IOException, SAXException {
        controller.getCityMap().setHighlighted(null, null);
        XMLDeserializer.loadDistribution(controller.getCityMap());
        controller.setCurrentState(controller.DISTRIBUTION_STATE);
        controller.resetListOfCommands();

    }

    @Override
    public void modifyDistribution(Controller controller) {
        controller.getCityMap().setHighlighted(null, null);
        controller.ADD_STATE_1.entryAction(controller.getWindow());
        controller.setCurrentState(controller.ADD_STATE_1);
        controller.resetListOfCommands();

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
    public void leftClick(Controller controller, Window window, CityMap map, ListOfCommands listOfCommands,
                          Intersection intersection, PointOfInterest poi) {
        if (poi != null && !(poi instanceof DepotAddress)) {
            controller.HIGHLIGHT_STATE.entryAction(poi, map);
            controller.setCurrentState(controller.HIGHLIGHT_STATE);
        } else {
            map.setHighlighted(null, null);
            controller.setCurrentState(controller.TOUR_STATE);
        }
    }

    protected void entryAction(PointOfInterest poi, CityMap cityMap) {
        this.highlightpoint = poi;
        PointOfInterest secondaryPoint;
        if (this.highlightpoint instanceof PickupAddress) {
            secondaryPoint = cityMap.distribution.getDelivery((PickupAddress) highlightpoint);
        } else {
            secondaryPoint = cityMap.distribution.getPickup((DeliveryAddress) highlightpoint);
        }
        cityMap.setHighlighted(highlightpoint, secondaryPoint);
    }

    @Override
    public void generateRoadmap(Controller controller, Window window) {
        RoadMapGenerator.generateRoadmap(controller.getCityMap());
        controller.getCityMap().setHighlighted(null, null);
        controller.setCurrentState(controller.TOUR_STATE);
    }

    @Override
    public void undo(ListOfCommands listOfCommands) {
        listOfCommands.undo();
    }

    @Override
    public void redo(ListOfCommands listOfCommands) {
        listOfCommands.redo();
    }


    public void enableButtons(Window window, ListOfCommands loc) {
        window.enableButton("Load a city map", true);
        window.enableButton("Load a distribution", true);
        window.enableButton("Compute a tour", false);
        window.enableButton("Add request", true);
        window.enableButton("Remove", true);
        window.enableButton("Undo", loc.getCurrentIndex() >= 0);
        window.enableButton("Redo", loc.getCurrentIndex() < loc.getList().size() - 1);
        window.enableButton("Generate roadmap", true);

    }
}
