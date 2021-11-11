/**
 * SwapCommand
 *
 * @author 4IF-4114
 */
package controller;

import model.CityMap;
import model.DeliveryAddress;
import model.PickupAddress;
import model.PointOfInterest;

/**
 * The Command adding swaping the position of two points nearby
 */
public class SwapCommand implements Command {

    private final CityMap MAP;
    private final int SENS;
    private final int POSITION;
    private final PointOfInterest POI;
    private final boolean AUTHORIZED;

    /**
     * Constructor of SwapCommand
     *
     * @param map the citymap
     * @param idPOI id of the point to move
     * @param direction direction that the point needs to move to
     * @throws Exception if the movement is impossible
     */
    public SwapCommand(CityMap map, Integer idPOI, int direction) throws Exception {
        this.MAP = map;
        this.POI = map.distribution.getPointOfIntersection(idPOI);
        this.SENS = direction;
        this.POSITION = map.tour.getPointOfInterests().indexOf(POI);

        if (POSITION + direction == 0
                || POSITION + direction == map.tour.getPointOfInterests().size() - 1
                || (POI instanceof PickupAddress && map.tour.getPointOfInterests().get(POSITION + direction)
                    == map.distribution.getDelivery((PickupAddress) POI))
                || (POI instanceof DeliveryAddress && map.tour.getPointOfInterests().get(POSITION + direction)
                    == map.distribution.getPickup((DeliveryAddress) POI))) {
            this.AUTHORIZED = false;
            throw new Exception("Error : this action is impossible.");
        } else {
            this.AUTHORIZED = true;
        }
    }


    /**
     *do the command
     */
    public void doCommand() {
        if (AUTHORIZED)
            MAP.changePosition(POI, POSITION + SENS);
    }

    /**
     *undo the command
     */
    public void undoCommand() {
        if (AUTHORIZED)
            MAP.changePosition(POI, POSITION);
    }
}

