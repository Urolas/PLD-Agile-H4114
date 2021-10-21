package model;

import java.util.*;

/**
 * @author 4IF-4114
 */
public class Distribution {

    private DepotAddress depot;
    private Set<Request> requests;

    /**
     * Default constructor
     */
    public Distribution() {
        this.requests = new HashSet<Request>();
        this.depot = new DepotAddress();
    }

    public void reset() {
        this.requests.clear();
        this.depot = new DepotAddress();
    }

    public void addDepot(Intersection i, String departureTime) {
        this.depot = new DepotAddress(i, departureTime);
    }

    public void addRequest(Integer pickupDuration, Integer deliveryDuration, Intersection pintersection, Intersection dintersection, Integer i) {
        PickupAddress pAddress = new PickupAddress(pintersection, pickupDuration,i);
        DeliveryAddress dAddress = new DeliveryAddress(dintersection, deliveryDuration,i+1);
        this.requests.add(new Request(pAddress,dAddress));

    }

    //return the Id of each point of interest, the first one is always the depot point
    public List<PointOfInterest> GetAllPoints(){
        List<PointOfInterest> points = new ArrayList<>();
        points.add(this.depot);
        for (Request request: this.requests) {
            points.add(request.getDelivery());
            points.add(request.getPickup());
        }
        return points;
    }

    public List<AbstractMap.SimpleEntry<String, String>> GetConstraints() {
        List<AbstractMap.SimpleEntry<String, String>> result = new ArrayList<>();
        for (Request request: this.requests) {
            result.add(new AbstractMap.SimpleEntry<>(request.getPickup().intersection.id,request.getDelivery().intersection.id));
        }

        return result;
    }
}