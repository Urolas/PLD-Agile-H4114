package controller;

import model.CityMap;
import model.Intersection;
import model.PointOfInterest;
import view.Window;

import java.awt.*;

public class AddState4 implements State {
    private Intersection i1;
    private Integer d1;
    private PointOfInterest p1;
    private Intersection i2;
    private Integer d2;

    public void leftClick(Controller c, Window window, CityMap map, ListOfCommands listOfCommands, Intersection i, PointOfInterest poi) {
        if (poi != null) {
            try {
                listOfCommands.add(new AddCommand(map, this.i1,this.d1, this.p1, this.i2,this.d2, poi));
            } catch (Exception e) {
                System.out.println(e);
            }
            c.getCitymap().resetSelected();
            c.setCurrentState(c.tourState);
            window.displayMessage("");
        } else {
            window.parsingError("No point error: Click on a valid point.");

        }

    }
    @Override
    public void rightClick(Controller c){
        c.getCitymap().resetSelected();
        c.setCurrentState(c.tourState);
    }

    public void entryAction(Intersection i1,Integer d1 ,PointOfInterest p, Intersection i2,Integer d2) {
        this.i1 = i1;
        this.d1=d1;
        this.p1 = p;
        this.i2 = i2;
        this.d2=d2;
    }
}
