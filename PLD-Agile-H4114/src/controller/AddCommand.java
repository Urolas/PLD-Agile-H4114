/**
 * AddCommand
 *
 * @author 4IF-4114
 */

package controller;

import model.*;

/**
 * The Command adding points of interest to the distribution
 */
public class AddCommand implements Command {

    private final CityMap MAP;
    private final PickupAddress POI_P;
    private final DeliveryAddress POI_D;
    private final PointOfInterest PRE_PICKUP;
    private final PointOfInterest PRE_DELIVERY;
    private boolean authorized;

    /**
     * Constructor of AddCommand
     *
     * @param i1  the intersection where the new Pickup will be placed
     * @param i2  the intersection where the new Delivery will be placed
     * @param d1  the duration of the new Pickup
     * @param d2  the duration of the new Delivery
     * @param p1  the point before the new Pickup
     * @param p2  the point before the new Delivery
     * @param map the citymap
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
     * Do the command
     */
    @Override
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
     * Undo the command
     */
    @Override
    public void undoCommand() {
        if (authorized) {
            MAP.removeRequest(POI_P, POI_D);
            MAP.distribution.removeRequest(POI_P, POI_D);
        }
    }

}