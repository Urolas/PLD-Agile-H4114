/**
 * DeleteCommand
 *
 * @author 4IF-4114
 */
package controller;

import model.*;

/**
 * The Command removing points of interest from the distribution
 */
public class DeleteCommand implements Command {

    private final CityMap CITY_MAP;
    private final PointOfInterest POI;
    private final PickupAddress POI_PICKUP;
    private final DeliveryAddress POI_DELIVERY;
    private final PointOfInterest PRE_PICKUP;
    private final PointOfInterest PRE_DELIVERY;

    /**
     * Constructor of DeleteCommand
     *
     * @param cityMap the citymap
     * @param poi     Point of interest to delete
     */
    public DeleteCommand(CityMap cityMap, PointOfInterest poi) {
        this.CITY_MAP = cityMap;
        this.POI = poi;
        if (poi instanceof PickupAddress) {
            this.POI_PICKUP = (PickupAddress) poi;
            this.POI_DELIVERY = cityMap.distribution.getDelivery(POI_PICKUP);
        } else {
            this.POI_DELIVERY = (DeliveryAddress) poi;
            this.POI_PICKUP = cityMap.distribution.getPickup(POI_DELIVERY);
        }
        this.PRE_PICKUP = cityMap.tour.getPointBefore(POI_PICKUP);
        this.PRE_DELIVERY = cityMap.tour.getPointBefore(POI_DELIVERY);
    }

    /**
     * do the command
     */
    @Override
    public void doCommand() {
        if (!(POI instanceof DepotAddress))
            CITY_MAP.removeRequest(POI_PICKUP, POI_DELIVERY);
    }

    /**
     *undo the command
     */
    @Override
    public void undoCommand() {
        try {
            CITY_MAP.addRequest(POI_PICKUP, PRE_PICKUP, POI_DELIVERY, PRE_DELIVERY);
            CITY_MAP.distribution.addRequest(POI_PICKUP, POI_DELIVERY, POI_PICKUP.getIdPointOfInterest());
        } catch (Exception ignored) {
        }
    }

}