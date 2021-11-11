package controller;


import model.*;

/**
 * @author 4IF-4114
 */
public class AddCommand implements Command {

    private CityMap map;
    private PickupAddress poiP;
    private DeliveryAddress poiD;
    private PointOfInterest preP;
    private PointOfInterest preD;
    private boolean authorized;
    /**
     * Default constructor
     */


    public AddCommand(CityMap map, Intersection i1,Integer d1, PointOfInterest p1, Intersection i2,Integer d2, PointOfInterest p2) {
        this.map=map;
        this.preP=p1;
        this.preD=p2;

        this.poiP= new PickupAddress(i1,d1,map.tour.getPointOfInterests().size());
        this.poiD= new DeliveryAddress(i2,d2,map.tour.getPointOfInterests().size()+1);

        this.authorized=true;


    }


    /**
     * @return
     */
    public void doCommand() throws Exception{
        try {
            map.addRequest(poiP,preP,poiD,preD);
            map.distribution.addRequest(poiP,poiD,poiP.getIdPointOfInterest());
        } catch ( Exception e){
            this.authorized=false;
            throw  e;
        }
    }

    /**
     * @return
     */
    public void undoCommand() {
        if (authorized) {
            map.removeRequest(poiP, poiD);
            map.distribution.removeRequest(poiP, poiD);
        }
    }

}