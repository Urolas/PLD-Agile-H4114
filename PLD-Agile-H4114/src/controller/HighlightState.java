package controller;

import model.CityMap;
import model.Intersection;
import model.PointOfInterest;
import model.Tour;
import view.Window;

import java.awt.*;

public class HighlightState implements State {
    private PointOfInterest highlightpoint;

    @Override
    public void removePointOfInterest(Controller c, Window w, CityMap map, ListOfCommands listOfCommands) {

        try {
            listOfCommands.add(new DeleteCommand(map, this.highlightpoint));
        } catch (Exception e) {
            e.printStackTrace();
        }
        c.setCurrentState(c.tourState);

    }

    protected void entryAction(PointOfInterest poi) {
        this.highlightpoint=poi;
    }

}
