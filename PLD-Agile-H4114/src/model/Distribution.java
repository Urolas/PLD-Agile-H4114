package model;

import view.MapView;

import java.util.*;

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

    public void addRequest(Integer pickupDuration, Integer deliveryDuration, Intersection pintersection, Intersection dintersection,Integer i) {
        PickupAddress pAddress = new PickupAddress(pintersection, pickupDuration,i);
        DeliveryAddress dAddress = new DeliveryAddress(dintersection, deliveryDuration,i+1);
        Request r = new Request(pAddress,dAddress);
        this.requests.add(r);
        notifyObservers(r);
    }

    public void addObserver(MapView mapView) {
        super.addObserver(mapView);
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

    @Override
    public boolean equals(Object o){
        if(o.getClass()!=this.getClass()){
            return false;
        }
        if(!this.depot.equals(((Distribution) o).depot)){
            return false;
        }
        if(!this.requests.equals(((Distribution) o).requests)){
            return false;
        }
        return true;
    }
}