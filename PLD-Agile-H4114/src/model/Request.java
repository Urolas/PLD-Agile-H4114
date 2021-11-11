/**
 * Request
 * @author 4IF-4114
 */
package model;

import java.awt.*;

/**
 * A Request is composed of a pickup address and a delivery address
 */
public class Request {
    public Color color;
    private PickupAddress pickup;
    private DeliveryAddress delivery;

    /**
     * Default constructor of Request
     */
    public Request() {
    }

    /**
     * Constructor of Request
     * @param pickupAddress the pickup address of the request
     * @param deliveryAddress the delivery address of the request
     * @param c the color of this request shown on the map
     */
    public Request(PickupAddress pickupAddress, DeliveryAddress deliveryAddress,Color c){
        this.color = c;
        this.pickup=pickupAddress;
        this.pickup.color=c;
        this.delivery=deliveryAddress;
        this.delivery.color=c;
    }

    public DeliveryAddress getDelivery() {
        return delivery;
    }

    public PickupAddress getPickup() {
        return pickup;
    }

    @Override
    public String toString() {
        return "Request{" +
                "color=" + color +
                ", pickup=" + pickup +
                ", delivery=" + delivery +
                '}';
    }

    /**
     * Compares this request with another object and check if they are equal
     * @param obj the object to be compared with
     */
    @Override
    public boolean equals(Object o){
        if(!this.color.equals(((Request) o).color)){
            return false;
        }
        if(!this.pickup.equals(((Request) o).pickup)){
            return false;
        }
        if(!this.delivery.equals(((Request) o).delivery)){
            return false;
        }
        return true;
    }
}