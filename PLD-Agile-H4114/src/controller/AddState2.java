package controller;

import model.CityMap;
import model.Intersection;
import model.PointOfInterest;
import view.Window;

import java.awt.*;

public class AddState2 implements State {
    private Intersection i1;
    private Integer d1;


    public void leftClick(Controller c, Window window, CityMap map, ListOfCommands listOfCommands, Intersection i, PointOfInterest poi) {

        if (poi != null) {

            c.addState3.entryAction(this.i1,d1, poi,window);
            c.setCurrentState(c.addState3);
            window.displayMessage("Place the DeliveryPoint : \nenter a duration (in sec)");

        } else {
            window.displayMessage("No point error: After which point");

        }


    }
    @Override
    public void rightClick(Controller c){
        c.getCitymap().resetSelected();
        c.setCurrentState(c.tourState);
    }

    protected void entryAction(Intersection i,Integer d) {
        this.i1 = i;
        this.d1=d;
    }



}
