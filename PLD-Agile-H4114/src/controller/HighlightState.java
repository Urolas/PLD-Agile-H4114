package controller;

import filecontrol.RoadMapGenerator;
import model.*;
import view.Window;

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
        c.setCurrentState(c.tourState);
    }

    public void enableButtons(Window window, ListOfCommands loc) {
        window.enableButton("Load a city map", false);
        window.enableButton("Load a distribution", false);
        window.enableButton("Compute a tour", false);
        window.enableButton("Add request", false);
        window.enableButton("Remove", true);
        window.enableButton("Redo", false);
        window.enableButton("Undo", false);
        window.enableButton("Generate roadmap", true);

    }
}
