package controller;

import model.CityMap;
import model.Intersection;
import model.PointOfInterest;
import view.Window;

public class AddState1 implements State {
    private Integer d1;

    @Override
    public void leftClick(Controller c, Window window, CityMap map, ListOfCommands listOfCommands,
                          Intersection i, PointOfInterest poi) {

        String strDuration = window.getDuration();

        try {
            this.d1 = Integer.parseInt(strDuration);
        } catch (NumberFormatException e) {
            window.parsingError("Wrong format value\n" + e.getMessage());
            return;
        }

        if (i != null) {
            c.addState2.entryAction(i, this.d1);
            c.setCurrentState(c.addState2);
            map.setSelected1(i);

            map.setPOIToAdd(null);
            window.displayMessage("After which point of interest ?");
        } else {
            window.parsingError("Wrong point placement error : Click on a valid intersection");
        }
    }


    @Override
    public void rightClick(Controller c) {
        c.getCitymap().resetSelected();
        c.setCurrentState(c.tourState);
    }

    protected void entryAction(Window w) {
        this.d1 = 300;
        w.displayMessage("Choose the pickup duration and\nchoose the pickup point position on the map.");
        w.resetDurationInserted();
    }


    public void enableButtons(Window window, ListOfCommands loc) {
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
        controller.getCitymap().setPOIToAdd(intersection);
    }
}
