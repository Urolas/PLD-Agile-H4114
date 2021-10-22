package model;

import view.MapView;

import java.awt.*;
import java.util.*;
import java.util.List;

import observer.Observable;

/**
 * @author 4IF-4114
 */
public class Distribution extends Observable{

    private DepotAddress depot;
    private Set<Request> requests;
    private List<String> requestColors = new ArrayList<>();

    /**
     * Default constructor
     */
    public Distribution() {
        this.requests = new HashSet<Request>();
        this.depot = new DepotAddress();
        this.requestColors.add("#1FBED6");
        this.requestColors.add("#97C30A");
        this.requestColors.add("#FF717E");
        this.requestColors.add("#FFDE00");
        this.requestColors.add("#006666");
        this.requestColors.add("#FFFFFF");

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
        System.out.println(i);
        Request r;
        if((i-1)/2<this.requestColors.size()){
            r = new Request(pAddress,dAddress,Color.decode(this.requestColors.get((i-1)/2)));
        } else {

            r = new Request(pAddress,dAddress,new Color((int) (Math.random() * 0x1000000)));

        }
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