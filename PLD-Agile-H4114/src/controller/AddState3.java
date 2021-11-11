package controller;

import model.CityMap;
import model.Intersection;
import model.PointOfInterest;
import view.Window;

import java.awt.*;

public class AddState3 implements State {
    private Intersection i1;
    private PointOfInterest p1;
    private Integer d1;
    private Integer d2;

    public void leftClick(Controller c, Window window, CityMap map, ListOfCommands listOfCommands, Intersection i, PointOfInterest poi) {
        String strDuration = window.getDuration();


        if (i != null) {
            try {
                this.d2 = Integer.parseInt(strDuration);
                if(d2<0) throw new NumberFormatException();
            }
            catch (NumberFormatException e) {
                window.parsingError("Incorrect value\nPlease enter a positive number\nand place the point");
                return;
            }
            c.addState4.entryAction(this.i1,this.d1, this.p1, i,this.d2);
            c.setCurrentState(c.addState4);
            map.setSelected2(i);
            map.setPoiToAdd(null);
            window.displayMessage("After which point ?\nSelect a point of interest on the map.");
            window.enableJtextField(false);

        } else {
            window.parsingError("Misplaced point error: Click on a valid intersection.");

        }


    }

    @Override
    public void rightClick(Controller c){
        c.getCitymap().resetSelected();
        c.setCurrentState(c.tourState);
    }
    public void entryAction(Intersection i1,Integer d, PointOfInterest p,Window w) {
        this.i1 = i1;
        this.p1 = p;
        this.d1=d;
        this.d2=300;
        w.resetDurationInserted();
    }


    public void mouseMoved(Controller controller, Intersection intersection) {
        controller.getCitymap().setPoiToAdd(intersection);
    }
}
