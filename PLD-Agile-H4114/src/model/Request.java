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



    public Request(PickupAddress pickupAddress,DeliveryAddress deliveryAddress){
        this.pickup=pickupAddress;
        this.delivery=deliveryAddress;
    }

}