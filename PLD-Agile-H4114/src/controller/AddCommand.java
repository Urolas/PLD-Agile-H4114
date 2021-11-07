package controller;


import model.*;

/**
 * @author 4IF-4114
 */
public class AddCommand implements Command {

    private CityMap map;
    private PointOfInterest poiP;
    private PointOfInterest poiD;
    private PointOfInterest preP;
    private PointOfInterest preD;
    /**
     * Default constructor
     */


    public AddCommand(CityMap map, Intersection i1, PointOfInterest p1, Intersection i2, PointOfInterest p2) {
        this.map=map;
        this.preP=p1;
        this.preD=p2;
        this.poiP= new PickupAddress(i1,0,map.tour.getPointOfInterests().size());
        this.poiD= new DeliveryAddress(i2,0,map.tour.getPointOfInterests().size()+1);

    }


    /**
     * @return
     */
    public void doCommand() throws Exception {
        map.addRequest(poiP,preP,poiD,preD);
        map.distribution.addRequest(poiP.getDuration(),poiD.getDuration(),poiP.getIntersection(),poiD.getIntersection(),poiP.getIdPointOfInterest());
    }

    /**
     * @return
     */
    public void undoCommand() {
        // TODO implement here
    }

}