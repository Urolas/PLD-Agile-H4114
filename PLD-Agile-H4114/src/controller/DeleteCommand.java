package controller;


import model.PointOfInterest;
import model.Tour;

/**
 * @author 4IF-4114
 */
public class DeleteCommand implements Command {

    private Tour tour;
    private PointOfInterest poi;

    /**
     * Default constructor
     */
    public DeleteCommand(Tour tour, PointOfInterest poi) {
        this.tour = tour;
        this.poi = poi;
    }


    /**
     * @return
     */
    public void doCommand() {
        tour.remove(poi);
    }

    /**
     * @return
     */
    public void undoCommand() {
        // TODO implement here
    }

}