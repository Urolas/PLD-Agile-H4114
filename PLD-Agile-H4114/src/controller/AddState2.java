package controller;

import model.CityMap;
import model.Intersection;
import model.PointOfInterest;
import view.Window;

import java.awt.*;

public class AddState2 implements State  {
    private Intersection i1;


    public void leftClick(Controller c, Window window, CityMap map, ListOfCommands listOfCommands, double cLong, double cLat) {
        PointOfInterest poi = map.getClosestPOI(cLong,cLat);
        c.addState3.entryAction(this.i1,poi);
        c.setCurrentState(c.addState3);

    }
    protected void entryAction(Intersection i) {
        this.i1 = i1;
    }

}
