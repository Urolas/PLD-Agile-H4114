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

    public void addRequest(Integer pickupDuration, Integer deliveryDuration, Intersection pintersection, Intersection dintersection) {
        PickupAddress pAddress = new PickupAddress(pintersection, pickupDuration);
        DeliveryAddress dAddress = new DeliveryAddress(dintersection, deliveryDuration);
        this.requests.add(new Request(pAddress,dAddress));

    }
    public List<String> GetAllPoints(){
        List<String> points = new ArrayList<>();
        points.add(this.depot.intersection.id);
        for (Request request: this.requests) {
            points.add(request.getDelivery().intersection.id);
            points.add(request.getPickup().intersection.id);
        }
        return points;
    }

}