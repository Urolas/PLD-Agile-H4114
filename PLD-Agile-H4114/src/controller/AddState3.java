package controller;

import model.CityMap;
import model.Intersection;
import model.PointOfInterest;
import view.Window;

public class AddState3 implements State {
    private Intersection i1;
    private PointOfInterest p1;
    private Integer d1;
    private Integer d2;

    public void leftClick(Controller c, Window window, CityMap map, ListOfCommands listOfCommands, Intersection i, PointOfInterest poi) {
        String strDuration = window.getDuration();

        try {
            this.d2 = Integer.parseInt(strDuration);

        } catch (NumberFormatException e) {
            window.parsingError("Wrong format value\n" + e.getMessage());
            return;
        }
        if (i != null) {
            c.addState4.entryAction(this.i1, this.d1, this.p1, i, this.d2);
            c.setCurrentState(c.addState4);
            map.setSelected2(i);
            map.setPOIToAdd(null);
            window.displayMessage("Apres quel point");
        } else {
            window.displayMessage("Erreur point mal placé : Placez le deliveryPoint");

        }


    }

    @Override
    public void rightClick(Controller c) {
        c.getCitymap().resetSelected();
        c.setCurrentState(c.tourState);
    }

    public void entryAction(Intersection i1, Integer d, PointOfInterest p, Window w) {
        this.i1 = i1;
        this.p1 = p;
        this.d1 = d;
        this.d2 = 300;
        w.resetDurationInserted();
    }


    public void mouseMoved(Controller controller, Intersection intersection) {
        controller.getCitymap().setPOIToAdd(intersection);
    }
}
