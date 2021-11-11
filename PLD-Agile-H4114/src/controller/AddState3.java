package controller;

import model.CityMap;
import model.Intersection;
import model.PointOfInterest;
import view.Window;

import java.awt.*;

public class AddState3 implements State {
    private Intersection i1;
    private PointOfInterest p1;

    public void leftClick(Controller c, Window window, CityMap map, ListOfCommands listOfCommands, Intersection i, PointOfInterest poi) {
        if (i != null) {
            c.addState4.entryAction(this.i1, this.p1, i);
            c.setCurrentState(c.addState4);
            map.setSelected2(i);
            map.setPoiToAdd(null);
            window.displayMessage("Apres quel point");
        } else {
            window.displayMessage("Erreur point mal placé : Placez le deliveryPoint");

        }


    }

    @Override
    public void rightClick(Controller c){
        c.getCitymap().resetSelected();
        c.setCurrentState(c.tourState);
    }
    public void entryAction(Intersection i1, PointOfInterest p) {
        this.i1 = i1;
        this.p1 = p;
    }

    public void mouseMoved(Controller controller, Intersection intersection) {
        controller.getCitymap().setPoiToAdd(intersection);
    }
}
