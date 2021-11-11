package controller;


import model.*;

/**
 * @author 4IF-4114
 */
public class DeleteCommand implements Command {

    private final CityMap CITY_MAP;
    private final PointOfInterest POI;
    private final PickupAddress POI_PICKUP;
    private final DeliveryAddress POI_DELIVERY;
    private final PointOfInterest PRE_PICKUP;
    private final PointOfInterest PRE_DELIVERY;

    /**
     * Default constructor
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
     *
     */
    public void doCommand() {
        if (!(POI instanceof DepotAddress))
            CITY_MAP.removeRequest(POI_PICKUP, POI_DELIVERY);
    }

    /**
     *
     */
    public void undoCommand() {
        try {
            CITY_MAP.addRequest(POI_PICKUP, PRE_PICKUP, POI_DELIVERY, PRE_DELIVERY);
            CITY_MAP.distribution.addRequest(POI_PICKUP, POI_DELIVERY, POI_PICKUP.getIdPointOfInterest());
        } catch (Exception ignored) {
        }
    }

}