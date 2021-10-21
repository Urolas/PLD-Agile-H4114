package model;

/**
 * @author 4IF-4114
 */
public class Request {
    private PickupAddress pickup;
    private DeliveryAddress delivery;

    /**
     * Default constructor
     */
    public Request() {
    }

    public PickupAddress getPickup() {
        return pickup;
    }

    public DeliveryAddress getDelivery() {
        return delivery;
    }

    public Request(PickupAddress pickupAddress, DeliveryAddress deliveryAddress){
        this.pickup=pickupAddress;
        this.delivery=deliveryAddress;
    }

}