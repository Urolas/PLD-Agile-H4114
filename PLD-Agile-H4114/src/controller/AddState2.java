package controller;

import model.CityMap;
import model.Intersection;
import model.PointOfInterest;
import view.Window;

import java.awt.*;

public class AddState2 implements State {
    private Intersection i1;


    public void leftClick(Controller c, Window window, CityMap map, ListOfCommands listOfCommands, Intersection i, PointOfInterest poi) {

        if (poi != null) {

            c.addState3.entryAction(this.i1, poi);
            c.setCurrentState(c.addState3);
            window.displayMessage("Placez le deliveryPoint");

        } else {
            window.displayMessage("Erreur aucun point : Apres quel point");

        }


    }
    @Override
    public void rightClick(Controller c){
        c.getCitymap().resetSelected();
        c.setCurrentState(c.tourState);
    }

    protected void entryAction(Intersection i) {
        this.i1 = i;
    }

}
