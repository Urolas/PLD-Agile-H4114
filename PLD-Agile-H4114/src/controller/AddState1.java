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

    protected void entryAction(Window w) {
        w.displayMessage("Placez le pickupPoint");
    }

    public  void enableButtons(Window window, ListOfCommands loc) {
        window.enableButton("Load a city map", true);
        window.enableButton("Load a distribution", true);
        window.enableButton("Compute a tour", false);
        window.enableButton("Modify the distribution", false);
        window.enableButton("Remove", false);
        window.enableButton("Redo", false);
        window.enableButton("Undo", false);
    }
}
