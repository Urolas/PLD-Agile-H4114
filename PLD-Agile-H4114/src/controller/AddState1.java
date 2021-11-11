package controller;

import model.CityMap;
import model.Intersection;
import model.PointOfInterest;
import view.Window;

import java.awt.Point;

public class AddState1 implements State {
    private Integer d1;
    @Override
    public void leftClick(Controller c, Window window, CityMap map, ListOfCommands listOfCommands, Intersection i, PointOfInterest poi) {

        if (i != null) {

            c.addState2.entryAction(i,this.d1);
            c.setCurrentState(c.addState2);
            map.setSelected1(i);

            map.setPoiToAdd(null);
            window.displayMessage("Apres quel point");
        } else {
            window.displayMessage("Erreur point mal placé : Placez le pickupPoint");

        }
    }



    @Override
    public void rightClick(Controller c){
        c.getCitymap().resetSelected();
        c.setCurrentState(c.tourState);
    }

    protected void entryAction(Window w) {
        this.d1=300;
        w.displayMessage("Placez le pickupPoint :\n renseignez une durée");
    }

    @Override
    public void addDuration(Integer duration){
        this.d1=duration;

    }

    public  void enableButtons(Window window, ListOfCommands loc) {
        window.enableButton("Load a city map", true);
        window.enableButton("Load a distribution", true);
        window.enableButton("Compute a tour", false);
        window.enableButton("Add request", false);
        window.enableButton("Remove", false);
        window.enableButton("Redo", false);
        window.enableButton("Undo", false);
        window.enableButton("Generate roadmap", false);

    }

    public void mouseMoved(Controller controller, Intersection intersection) {
        controller.getCitymap().setPoiToAdd(intersection);
    }
}
