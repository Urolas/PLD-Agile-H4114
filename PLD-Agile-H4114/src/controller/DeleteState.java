package controller;

import model.PointOfInterest;
import model.Tour;

import java.awt.*;

public class DeleteState implements State {

    @Override
    public void leftClick(Controller controller, Window window, Tour tour, ListOfCommands listOfCommands, Point p) {
        PointOfInterest poi = tour.searchPointOfInterest(p);
        if (poi != null)
            listOfCommands.add(new DeleteCommand(tour, poi));
    }
}
