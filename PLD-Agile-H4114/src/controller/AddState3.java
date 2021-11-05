package controller;

import model.CityMap;
import model.Intersection;
import model.PointOfInterest;
import view.Window;

import java.awt.*;

public class AddState3 implements State  {
    private Intersection i1;
    private PointOfInterest p1;
    public void leftClick(Controller c, Window window, CityMap map, ListOfCommands listOfCommands, Point p) {
        Intersection i = map.getClosestIntersection(p);
        c.addState4.entryAction(this.i1,this.p1,i);

    }
    public void entryAction(Intersection i1, PointOfInterest p) {
        this.i1=i1;
        this.p1=p;
    }
}
