package controller;


import model.CityMap;
import model.DepotAddress;
import model.PointOfInterest;
import model.Tour;

/**
 * @author 4IF-4114
 */
public class DeleteCommand implements Command {

    private CityMap cityMap;
    private PointOfInterest poi;

    /**
     * Default constructor
     */
    public DeleteCommand(CityMap cityMap, PointOfInterest poi) {
        this.cityMap = cityMap;
        this.poi = poi;
    }

    /**
     * @return
     */
    public void doCommand() {
        if (!(poi instanceof DepotAddress))
            cityMap.removeRequest(poi);
    }

    /**
     * @return
     */
    public void undoCommand() {
        // TODO implement here
    }

}