package controller;

import model.CityMap;
import model.Intersection;
import view.Window;

import java.awt.Point;

public class AddState1 implements State {
    @Override
    public void leftClick(Controller c, Window window, CityMap map, ListOfCommands listOfCommands, Point p) {
        Intersection i = map.getClosestIntersection(p);
        c.addState2.entryAction(i);
        c.setCurrentState(c.addState2);

    }
}
