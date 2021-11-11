package controller;

import model.CityMap;
import model.DeliveryAddress;
import model.PickupAddress;
import model.PointOfInterest;

/**
 * @author 4IF-4114
 */
public class SwapCommand implements Command {

    private final CityMap MAP;
    private final int SENS;
    private final int POSITION;
    private final PointOfInterest POI;
    private final boolean AUTHORIZED;

    /**
     * Default constructor
     */
    public SwapCommand(CityMap map, Integer idPOI, int sens) throws Exception {
        this.MAP = map;
        this.POI = map.distribution.getPointOfIntersection(idPOI);
        this.SENS = sens;
        this.POSITION = map.tour.getPointOfInterests().indexOf(POI);

        if (POSITION + sens == 0
                || POSITION + sens == map.tour.getPointOfInterests().size() - 1
                || (POI instanceof PickupAddress && map.tour.getPointOfInterests().get(POSITION + sens)
                    == map.distribution.getDelivery((PickupAddress) POI))
                || (POI instanceof DeliveryAddress && map.tour.getPointOfInterests().get(POSITION + sens)
                    == map.distribution.getPickup((DeliveryAddress) POI))) {
            this.AUTHORIZED = false;
            throw new Exception("Error : this action is impossible.");
        } else {
            this.AUTHORIZED = true;
        }
    }


    /**
     *
     */
    public void doCommand() {
        if (AUTHORIZED)
            MAP.changePosition(POI, POSITION + SENS);
    }

    /**
     *
     */
    public void undoCommand() {
        if (AUTHORIZED)
            MAP.changePosition(POI, POSITION);
    }
}

