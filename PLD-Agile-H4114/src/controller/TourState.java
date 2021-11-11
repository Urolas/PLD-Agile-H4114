package controller;


import model.CityMap;
import model.DepotAddress;
import model.Intersection;
import model.PointOfInterest;
import org.xml.sax.SAXException;
import view.MapView;
import view.Window;
import filecontrol.RoadMapGenerator;
import filecontrol.XMLDeserializer;
import filecontrol.XMLException;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
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
        XMLDeserializer.loadCityMap(c.getCitymap());
        w.getMapView().resetZoom();
        w.displayMessage("Please load a Distribution.");
        c.setCurrentState(c.citymapState);
    }

    @Override
    public void loadDistribution(Controller c, Window w) throws XMLException, ParserConfigurationException, IOException, SAXException {
        XMLDeserializer.loadDistribution(c.getCitymap());
        w.displayMessage("Distribution loaded.\nA tour can be computed.");
        c.setCurrentState(c.distributionState);
    }

    @Override
    public void modifyDistribution(Controller c) {
        c.addState1.entryAction(c.getWindow());
        c.setCurrentState(c.addState1);
    }

    @Override
    public void up(Integer id,ListOfCommands listOfCommands,Controller c){
        try {
            listOfCommands.add(new SwapCommand(c.getCitymap(),id,-1));

        } catch (Exception ignored){}

    }
    @Override
    public void down(Integer id,ListOfCommands listOfCommands,Controller c){
        try {
            listOfCommands.add(new SwapCommand(c.getCitymap(),id,1));
        } catch (Exception ignored){}

    }

    @Override
    public void keyStroke(MapView mapView, int keyCode) {
        mapView.moveMapView(keyCode);
    }

    @Override
    public void leftClick(Controller c, Window w, CityMap cityMap, ListOfCommands l, Intersection i , PointOfInterest poi){
        if (poi != null && !(poi instanceof DepotAddress))
            try{
                c.highlightState.entryAction(poi,cityMap,w);

                c.setCurrentState(c.highlightState);
            } catch (Exception e) {
                System.out.println(e);
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
        RoadMapGenerator.generateRoadmap(c.getCitymap());
        c.setCurrentState(c.tourState);
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
