/**
 * AddState3
 *
 * @author 4IF-4114
 */
package controller;

import model.CityMap;
import model.Intersection;
import model.PointOfInterest;
import view.Window;

/**
 * AddState3, a state used when the user want to add a delivery point
 */
public class AddState3 implements State {
    private Intersection i1;
    private PointOfInterest p1;
    private Integer d1;
    private Integer d2;

    public void leftClick(Controller controller, Window window, CityMap map, ListOfCommands listOfCommands, Intersection i, PointOfInterest poi) {
        String strDuration = window.getDuration();

        if (i != null) {
            try {
                this.d2 = Integer.parseInt(strDuration);
                if (d2 < 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                window.parsingError("Incorrect value\nPlease enter a positive number\nand place the point");
                return;
            }
            controller.ADD_STATE_4.entryAction(this.i1, this.d1, this.p1, i, this.d2);
            controller.setCurrentState(controller.ADD_STATE_4);
            map.setSelected2(i);
            map.setPOIToAdd(null);
            window.displayMessage("After which point ?\nSelect a point of interest on the map.");
            window.enableJtextField(false);

        } else {
            window.parsingError("Misplaced point error: Click on a valid intersection.");

        }


    }

    @Override
    public void rightClick(Controller c) {
        c.getCityMap().resetSelected();
        c.setCurrentState(c.TOUR_STATE);
    }

    public void entryAction(Intersection i1, Integer d, PointOfInterest p, Window w) {
        this.i1 = i1;
        this.p1 = p;
        this.d1 = d;
        this.d2 = 300;
        w.resetDurationInserted();
    }


    public void mouseMoved(Controller controller, Intersection intersection) {
        controller.getCityMap().setPOIToAdd(intersection);
    }
}
