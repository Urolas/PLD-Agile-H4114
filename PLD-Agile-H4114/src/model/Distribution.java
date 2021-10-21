package model;

import view.MapView;

import java.util.Set;
import java.util.HashSet;
import observer.Observable;

/**
 * @author 4IF-4114
 */
public class Distribution extends Observable{

    private DepotAddress depot;
    private Set<Request> requests;

    /**
     * Default constructor
     */
    public Distribution() {
        this.requests = new HashSet<Request>();
        this.depot = new DepotAddress();
    }

    public DepotAddress getDepot() {
        return depot;
    }

    public Set<Request> getRequests() {
        return requests;
    }

    public void reset() {
        this.requests.clear();
        this.depot = new DepotAddress();
        notifyObservers();
    }

    public void addDepot(Intersection i, String departureTime) {

        this.depot = new DepotAddress(i, departureTime);
        notifyObservers(i);
    }

    public void addRequest(Integer pickupDuration, Integer deliveryDuration, Intersection pintersection, Intersection dintersection) {
        PickupAddress pAddress = new PickupAddress(pintersection, pickupDuration);
        DeliveryAddress dAddress = new DeliveryAddress(dintersection, deliveryDuration);
        Request r = new Request(pAddress,dAddress);
        this.requests.add(r);
        notifyObservers(r);
    }

    public void addObserver(MapView mapView) {
        super.addObserver(mapView);
    }

}