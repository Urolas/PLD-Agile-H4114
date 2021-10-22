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
        this.delivery=deliveryAddress;
    }

    public DeliveryAddress getDelivery() {
        return delivery;
    }

    public PickupAddress getPickup() {
        return pickup;
    }
}