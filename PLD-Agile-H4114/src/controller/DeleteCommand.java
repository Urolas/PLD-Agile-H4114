package controller;


import model.*;
import view.Window;

/**
 * @author 4IF-4114
 */
public class DeleteCommand implements Command {

    private CityMap cityMap;
    private PointOfInterest poi;
    private PickupAddress poiP;
    private DeliveryAddress poiD;
    private PointOfInterest preP;
    private PointOfInterest preD;

    /**
     * Default constructor
     */
    public DeleteCommand(CityMap cityMap, PointOfInterest poi) {
        this.cityMap = cityMap;
        this.poi = poi;
        if (poi instanceof PickupAddress) {
            this.poiP = (PickupAddress) poi;
            this.poiD = cityMap.distribution.getDelivery(poiP);
        } else {
            this.poiD = (DeliveryAddress) poi;
            this.poiP = cityMap.distribution.getPickup(poiD);
        }
        this.preP = cityMap.tour.getPointBefore(poiP);
        this.preD = cityMap.tour.getPointBefore(poiD);
    }

    /**
     * @return
     */
    public void doCommand() {
        if (!(poi instanceof DepotAddress))
            cityMap.removeRequest(poiP, poiD);
    }

    /**
     * @return
     */
    public void undoCommand() {
        try {
            cityMap.addRequest(poiP, preP, poiD, preD);

        } catch ( Exception ignored){}
        cityMap.distribution.addRequest(poiP.getDuration(), poiD.getDuration(), poiP.getIntersection(), poiD.getIntersection(), poiP.getIdPointOfInterest());
    }

}