package controller;


import model.Intersection;
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


    public AddCommand(Tour tour,Intersection i1, PointOfInterest p1, Intersection i2, PointOfInterest p2) {
        tour.add(i1,p1,i2,p2);
    }


    /**
     * @return
     */
    public void doCommand() {
//        tour.add(poiP,preP,poiD,preD);
    }

    /**
     * @return
     */
    public void undoCommand() {
        // TODO implement here
    }

}