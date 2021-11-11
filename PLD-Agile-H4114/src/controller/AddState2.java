package controller;

import model.CityMap;
import model.Intersection;
import model.PointOfInterest;
import view.Window;

public class AddState2 implements State {

    private Intersection i1;
    private Integer d1;


    public void leftClick(Controller controller, Window window, CityMap map, ListOfCommands listOfCommands,
                          Intersection i, PointOfInterest poi) {

        if (poi != null) {
            controller.ADD_STATE_3.entryAction(this.i1,d1, poi,window);
            controller.setCurrentState(controller.ADD_STATE_3);

            window.displayMessage("Choose the delivery duration in sec and\nchoose the delivery point position on the map.");
            window.enableJtextField(true);
        } else {
            window.parsingError("No point error: Click on a valid point.");

        }

    }

    @Override
    public void rightClick(Controller c) {
        c.getCityMap().resetSelected();
        c.setCurrentState(c.TOUR_STATE);
    }

    protected void entryAction(Intersection i, Integer d) {
        this.i1 = i;
        this.d1 = d;
    }


}
