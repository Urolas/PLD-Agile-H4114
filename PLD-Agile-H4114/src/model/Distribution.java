/**
 * Distribution
 *
 * @author 4IF-4114
 */
package model;

import view.MapView;

import java.awt.*;
import java.util.*;
import java.util.List;

import observer.Observable;

/**
 * The Distribution containing the list of requests
 */
public class Distribution extends Observable {

    public Integer nbPointOfInterest;
    private DepotAddress depot;
    private final Set<Request> REQUESTS;
    private final List<String> REQUESTCOLORS = new ArrayList<>();

    /**
     * Constructor of Distribution
     */
    public Distribution() {
        this.REQUESTS = new HashSet<>();
        this.depot = new DepotAddress();
        this.nbPointOfInterest = 1;
        this.REQUESTCOLORS.add("#5186fc");
        this.REQUESTCOLORS.add("#fc5151");
        this.REQUESTCOLORS.add("#fca451");
        this.REQUESTCOLORS.add("#87fc51");
        this.REQUESTCOLORS.add("#51fcf6");
        this.REQUESTCOLORS.add("#b551fc");
        this.REQUESTCOLORS.add("#f651fc");
    }

    /**
     * Clear the Distribution data
     */
    public void reset() {

        this.REQUESTS.clear();
        this.depot = new DepotAddress();
        notifyObservers();
    }

    /**
     * Add the depot point to the distribution
     *
     * @param i             the intersection of the depot
     * @param departureTime the departure time of the depot
     */
    public void addDepot(Intersection i, String departureTime) {

        this.depot = new DepotAddress(i, departureTime);
        notifyObservers(i);
    }


    /**
     * Add a request to the Distribution
     *
     * @param pAddress the pickup address of the new request
     * @param dAddress the distribution address of the new request
     * @param id       the id of the request
     */
    public void addRequest(PickupAddress pAddress, DeliveryAddress dAddress, Integer id) {
        this.nbPointOfInterest += 2;
        Request r;
        if ((id - 1) / 2 < this.REQUESTCOLORS.size()) {
            r = new Request(pAddress, dAddress, Color.decode(this.REQUESTCOLORS.get((id - 1) / 2)));
        } else {

            r = new Request(pAddress, dAddress, new Color((int) (Math.random() * 0x1000000)));

        }
        this.REQUESTS.add(r);
        notifyObservers(r);
    }

    /**
     * Add a request to the Distribution
     *
     * @param pickupDuration   the duration the deliveryman can stay on the pickup point
     * @param deliveryDuration the duration the deliveryman can stay on the delivery point
     * @param pIntersection    the intersection of the pickup point
     * @param dIntersection    the intersection of the delivery point
     * @param id               the id of the request
     */
    public void addRequest(Integer pickupDuration, Integer deliveryDuration, Intersection pIntersection, Intersection dIntersection, Integer id) {
        PickupAddress pAddress = new PickupAddress(pIntersection, pickupDuration, id);
        DeliveryAddress dAddress = new DeliveryAddress(dIntersection, deliveryDuration, id + 1);
        addRequest(pAddress, dAddress, id);
    }


    public void addObserver(MapView mapView) {
        super.addObserver(mapView);
    }


    /**
     * Remove a request from the Distribution
     *
     * @param pickupAddress   the pickupaddress to be deleted
     * @param deliveryAddress the deliveryaddress to be deleted
     */
    public void removeRequest(PickupAddress pickupAddress, DeliveryAddress deliveryAddress) {
        Request requestToRemove = null;
        for (Request req : this.REQUESTS) {
            if (req.getPickup().equals((pickupAddress)) &&
                    req.getDelivery().equals(deliveryAddress)) {
                requestToRemove = req;
            }
        }
        REQUESTS.remove(requestToRemove);
    }


    /**
     * return the Id of each point of interest, the first one is always the depot point
     *
     * @return the list of all pointOfInterest
     */
    public List<PointOfInterest> GetAllPoints() {
        List<PointOfInterest> points = new ArrayList<>();
        points.add(this.depot);
        for (Request request : this.REQUESTS) {
            points.add(request.getDelivery());
            points.add(request.getPickup());
        }
        return points;
    }

    public DepotAddress getDepot() {
        return depot;
    }

    public Set<Request> getRequests() {
        return REQUESTS;
    }

    public List<String> getColorList() {
        return REQUESTCOLORS;
    }

    /**
     * Find the delivery address of a pickup address
     *
     * @param p the mentioned pickup address
     * @return the delivery address of the pickup
     */
    public DeliveryAddress getDelivery(PickupAddress p) {
        for (Request request : REQUESTS) {
            if (request.getPickup().equals(p)) {
                return request.getDelivery();
            }
        }
        return null;

    }

    /**
     * Find the pickup address of a delivery address
     *
     * @param d the mentioned delivery address
     * @return the pickup address of the delivery
     */
    public PickupAddress getPickup(DeliveryAddress d) {
        for (Request request : REQUESTS) {
            if (request.getDelivery().equals(d)) {
                return request.getPickup();
            }
        }
        return null;

    }

    /**
     * Check the constraints of the order of the points of interest
     *
     * @return a list of pairs of id of point of interest, each pair being a pickupId and its deliveryId
     */
    public HashMap<Integer, Integer> GetConstraints() {
        HashMap<Integer, Integer> result = new HashMap<>();
        for (Request request : this.REQUESTS) {
            result.put(request.getPickup().idPointOfInterest, request.getDelivery().idPointOfInterest);
        }
        return result;
    }

    /**
     * Compares the distribution with another object and check if they are equal
     *
     * @param o the object to be compared with
     */
    @Override
    public boolean equals(Object o) {
        if (o.getClass() != this.getClass()) {
            return false;
        }
        if (!this.depot.equals(((Distribution) o).depot)) {
            return false;
        }
        if (!this.REQUESTS.equals(((Distribution) o).REQUESTS)) {
            return false;
        }
        return true;
    }

    /**
     * Get a pointOfInterest from its id
     *
     * @param idPoi the id of the point
     * @return the related point of interest
     */
    public PointOfInterest getPointOfIntersection(Integer idPoi) {
        for (Request request : REQUESTS) {
            if (Objects.equals(idPoi, request.getDelivery().idPointOfInterest)) {
                return request.getDelivery();
            }
            if (Objects.equals(idPoi, request.getPickup().idPointOfInterest)) {
                return request.getPickup();
            }
        }
        return null;
    }
}