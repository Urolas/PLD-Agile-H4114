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
    private CityMap cityMap;  // TODO a enlever(surement)
    private Set<Request> requests;

    public void reset() {
        this.requests.clear();
        this.depot = new DepotAddress();
    }

    public void addDepot(String address, String departureTime) {
        Intersection i = cityMap.getIntersections().get(address);
        this.depot = new DepotAddress(i, departureTime);
    }

    public void addRequest(Integer pickupDuration, Integer deliveryDuration, String id1, String id2) {
        Intersection pintersection = cityMap.getIntersections().get(id1);
        Intersection dintersection = cityMap.getIntersections().get(id2);
        PickupAddress paddress = new PickupAddress(pintersection, pickupDuration);
        DeliveryAddress daddress = new DeliveryAddress(dintersection, deliveryDuration);
        this.requests.add(new Request(paddress,daddress));

    }

}