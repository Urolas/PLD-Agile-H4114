package controller;

import model.CityMap;
import model.Intersection;
import model.PointOfInterest;
import view.Window;

import java.awt.*;

public class AddState4 implements State  {
    private Intersection i1;
    private PointOfInterest p1;
    private Intersection i2;
    public void leftClick(Controller c, Window window, CityMap map, ListOfCommands listOfCommands, double cLong, double cLat) {
        PointOfInterest poi = map.getClosestPOI(cLong,cLat);
        listOfCommands.add(new AddCommand(map.getTour(),this.i1,this.p1,this.i2,poi);
        c.setCurrentState(c.tourState);
    }
    public void entryAction(Intersection i1, PointOfInterest p,Intersection i2) {
        this.i1=i1;
        this.p1=p;
        this.i2=i2;
    }
}
