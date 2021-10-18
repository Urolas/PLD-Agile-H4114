package model;

import java.util.*;

/**
 * @author 4IF-4114
 */
public class Distribution {

    /**
     * Default constructor
     */
    public Distribution() {
        this.requests = new HashSet<Request>();
        this.depot = new DepotAddress();
    }

    private DepotAddress depot;
    private Set<Request> requests;

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

}