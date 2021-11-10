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


    public AddCommand(CityMap map, Intersection i1, PointOfInterest p1, Intersection i2, PointOfInterest p2) {
        this.map=map;
        this.preP=p1;
        this.preD=p2;
        this.poiP= new PickupAddress(i1,0,map.distribution.nbPointOfInterest);
        this.poiD= new DeliveryAddress(i2,0,map.distribution.nbPointOfInterest+1);
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