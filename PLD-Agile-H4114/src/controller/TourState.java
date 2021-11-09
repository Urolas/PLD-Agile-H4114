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
    public TourState() {}

    @Override
    public void loadMap(Controller c, Window w) throws XMLException, ParserConfigurationException, IOException, SAXException {
        XMLDeserializer.loadCityMap(c.getCitymap());
        c.setCurrentState(c.citymapState);
    }

    @Override
    public void loadDistribution(Controller c, Window w) throws XMLException, ParserConfigurationException, IOException, SAXException {
        XMLDeserializer.loadDistribution(c.getCitymap());
        c.setCurrentState(c.distributionState);
    }

    @Override
    public void modifyDistribution(Controller c){
        c.addState1.entryAction(c.getWindow());
        c.setCurrentState(c.addState1);
    };

    @Override
    public void keyStroke(MapView mapView, int keyCode) {

    }

    @Override
    public void leftClick(Controller c, Window w, CityMap cityMap, ListOfCommands l, Intersection i , PointOfInterest poi){
        if (poi != null && !(poi instanceof DepotAddress))
            try{
                c.highlightState.entryAction(poi,cityMap,w);
                c.setCurrentState(c.highlightState);

            } catch (Exception e){
                System.out.println(e);
            }
    }
    @Override
    public void undo(ListOfCommands listOfCdes){
        listOfCdes.undo();
    }

    @Override
    public void redo(ListOfCommands listOfCdes) {
        listOfCdes.redo();
    }
    public void generateRoadmap(Controller c, Window w) throws IOException {
        RoadMapGenerator.generateRoadmap(c.getCitymap());
        c.setCurrentState(c.tourState);

    }
}