package controller;


import model.PointOfInterest;
import model.Tour;

/**
 * @author 4IF-4114
 */
public class AddCommand implements Command {

    private Tour tour;
    private PointOfInterest poiP;
    private PointOfInterest poiD;
    private PointOfInterest preP;
    private PointOfInterest preD;
    /**
     * Default constructor
     */
    public AddCommand(Tour tour, PointOfInterest poi1,PointOfInterest poi2) {
        this.tour = tour;
        this.poiP = poi1;
        this.poiD = poi2;
    }


    /**
     * @return
     */
    public void doCommand() {
        tour.add(poiP,preP,poiD,preD);
    }

    /**
     * @return
     */
    public void undoCommand() {
        // TODO implement here
    }

}