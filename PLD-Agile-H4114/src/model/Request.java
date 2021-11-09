package model;

import java.awt.*;

/**
 * @author 4IF-4114
 */
public class Request {
    public Color color;
    private PickupAddress pickup;
    private DeliveryAddress delivery;

    /**
     * Default constructor
     */
    public Request() {
    }


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