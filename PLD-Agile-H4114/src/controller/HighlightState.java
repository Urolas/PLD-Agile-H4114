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
    @Override
    public void leftClick(Controller c, Window window, CityMap map, ListOfCommands listOfCommands, Intersection i, PointOfInterest poi) {

        if (poi == null) {

            c.setCurrentState(c.tourState);
        } else {
            c.highlightState.entryAction(poi);
            c.setCurrentState(c.highlightState);
        }
    }

    protected void entryAction(PointOfInterest poi) {
        this.highlightpoint=poi;
    }

    public void enableButtons(Window window, ListOfCommands loc) {
        window.enableButton("Load a city map", true);
        window.enableButton("Load a distribution", true);
        window.enableButton("Compute a tour", false);
        window.enableButton("Modify the distribution", true);
        window.enableButton("Remove", true);
        window.enableButton("Redo", false);
        window.enableButton("Undo", false);
    }
}
