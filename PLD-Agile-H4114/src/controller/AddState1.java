package controller;

import model.CityMap;
import model.Intersection;
import model.PointOfInterest;
import view.Window;

import java.awt.Point;

public class AddState1 implements State {
    @Override
    public void leftClick(Controller c, Window window, CityMap map, ListOfCommands listOfCommands, Intersection i, PointOfInterest poi) {

        if (i != null) {

            c.addState2.entryAction(i);
            c.setCurrentState(c.addState2);
            window.displayMessage("Apres quel point");
        } else {
            window.displayMessage("Erreur point mal placé : Placez le pickupPoint");

        }
    }

    @Override
    public void rightClick(Controller c){
        c.setCurrentState(c.tourState);
    }

    protected void entryAction(Window w) {
        w.displayMessage("Placez le pickupPoint");
    }

}
