package controller;

import model.*;

/**
 * @author 4IF-4114
 */
public class AddCommand implements Command {

    private final CityMap MAP;
    private final PickupAddress POI_P;
    private final DeliveryAddress POI_D;
    private final PointOfInterest PRE_PICKUP;
    private final PointOfInterest PRE_DELIVERY;
    private boolean authorized;

    /**
     * Default constructor
     */
    public AddCommand(CityMap map,
                      Intersection i1, Integer d1, PointOfInterest p1,
                      Intersection i2, Integer d2, PointOfInterest p2) {
        this.MAP = map;
        this.PRE_PICKUP = p1;
        this.PRE_DELIVERY = p2;

        this.POI_P = new PickupAddress(i1, d1, map.tour.getPointOfInterests().size());
        this.POI_D = new DeliveryAddress(i2, d2, map.tour.getPointOfInterests().size() + 1);

        this.authorized = true;


    }


    /**
     *
     */
    public void doCommand() throws Exception {
        try {
            MAP.addRequest(POI_P, PRE_PICKUP, POI_D, PRE_DELIVERY);
            MAP.distribution.addRequest(POI_P, POI_D, POI_P.getIdPointOfInterest());
        } catch (Exception e) {
            this.authorized = false;
            throw e;
        }
    }

    /**
     *
     */
    public void undoCommand() {
        if (authorized) {
            MAP.removeRequest(POI_P, POI_D);
            MAP.distribution.removeRequest(POI_P, POI_D);
        }
    }

}