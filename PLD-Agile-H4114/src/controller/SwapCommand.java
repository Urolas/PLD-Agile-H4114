package controller;

import model.CityMap;
import model.DeliveryAddress;
import model.PickupAddress;
import model.PointOfInterest;

/**
 * @author 4IF-4114
 */
public class SwapCommand implements Command {

    private CityMap map;
    private int sens;
    private int position;
    private PointOfInterest poi;
    private boolean authorized;

    /**
     * Default constructor
     */


    public SwapCommand(CityMap map, Integer idpoi, int sens) throws Exception {
        this.map = map;
        this.poi = map.distribution.getPointOfIntersection(idpoi);
        this.sens = sens;
        this.position = map.tour.getPointOfInterests().indexOf(poi);

        if (position+sens == 0 || position+sens == map.tour.getPointOfInterests().size() - 1 ||
                (poi instanceof PickupAddress && map.tour.getPointOfInterests().get(position+sens)==map.distribution.getDelivery((PickupAddress) poi)) ||
                (poi instanceof DeliveryAddress && map.tour.getPointOfInterests().get(position+sens)==map.distribution.getPickup((DeliveryAddress) poi))) {
            authorized = false;
            throw new Exception("Error : this action is impossible");
        } else {
            authorized = true;
        }
    }


    /**
     * @return
     */
    public void doCommand() {
        if (authorized)
            map.changePosition(poi, position + sens);
    }

    /**
     * @return
     */
    public void undoCommand() {
        if (authorized)
            map.changePosition(poi, position);
    }
}

