package controller;

import filecontrol.RoadMapGenerator;
import filecontrol.XMLDeserializer;
import filecontrol.XMLException;
import model.*;
import org.xml.sax.SAXException;
import view.Window;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;

public class HighlightState implements State {
    private PointOfInterest highlightpoint;
    private PointOfInterest secondaryPoint;

    @Override
    public void removePointOfInterest(Controller c, Window w, CityMap map, ListOfCommands listOfCommands) {

        try {
            listOfCommands.add(new DeleteCommand(map, this.highlightpoint));
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.setHighlighted(null,null);
        c.setCurrentState(c.tourState);

    }

    @Override
    public void loadMap(Controller c, Window w) throws XMLException, ParserConfigurationException, IOException, SAXException {
        c.getCitymap().setHighlighted(null,null);

        XMLDeserializer.loadCityMap(c.getCitymap());
        c.setCurrentState(c.citymapState);


    }

    @Override
    public void loadDistribution(Controller c, Window w) throws XMLException, ParserConfigurationException, IOException, SAXException {
        c.getCitymap().setHighlighted(null,null);

        XMLDeserializer.loadDistribution(c.getCitymap());

        c.setCurrentState(c.distributionState);
    }

    @Override
    public void modifyDistribution(Controller c) {
        c.getCitymap().setHighlighted(null,null);

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
    public void leftClick(Controller c, Window window, CityMap map, ListOfCommands listOfCommands, Intersection i, PointOfInterest poi) {


        if (poi != null  && !(poi instanceof DepotAddress)) {

            c.highlightState.entryAction(poi,map,window);
            c.setCurrentState(c.highlightState);
        } else {
            map.setHighlighted(null,null);

            c.setCurrentState(c.tourState);
        }
    }

    protected void entryAction(PointOfInterest poi,CityMap cityMap,Window window) {
        this.highlightpoint=poi;
        if(this.highlightpoint instanceof PickupAddress){
            this.secondaryPoint=cityMap.distribution.getDelivery((PickupAddress) highlightpoint);
        } else {
            this.secondaryPoint=cityMap.distribution.getPickup((DeliveryAddress) highlightpoint);
        }
        cityMap.setHighlighted(highlightpoint,secondaryPoint);


    }

    @Override
    public void generateRoadmap(Controller c, Window w) throws IOException {
        RoadMapGenerator.generateRoadmap(c.getCitymap());
        c.getCitymap().setHighlighted(null,null);
        c.setCurrentState(c.tourState);
    }

    @Override
    public void undo(ListOfCommands listOfCdes) {
        listOfCdes.undo();
    }

    @Override
    public void redo(ListOfCommands listOfCdes) {
        listOfCdes.redo();
    }


    public void enableButtons(Window window, ListOfCommands loc) {
        window.enableButton("Load a city map", true);
        window.enableButton("Load a distribution", true);
        window.enableButton("Compute a tour", false);
        window.enableButton("Add request", true);

        window.enableButton("Remove", true);
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
        window.enableButton("Generate roadmap", true);

    }
}
