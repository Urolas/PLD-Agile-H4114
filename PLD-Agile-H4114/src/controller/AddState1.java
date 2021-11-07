package controller;

import model.CityMap;
import model.Intersection;
import model.PointOfInterest;
import view.Window;

import java.awt.Point;

public class AddState1 implements State {
    @Override
    public void leftClick(Controller c, Window window, CityMap map, ListOfCommands listOfCommands, Intersection i , PointOfInterest poi) {

        c.addState2.entryAction(i);
        c.setCurrentState(c.addState2);
        System.out.println("rentre point");

    }
}
