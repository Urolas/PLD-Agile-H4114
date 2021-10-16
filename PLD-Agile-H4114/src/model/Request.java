package model;

/**
 * @author 4IF-4114
 */
public class Request {

    /**
     * Default constructor
     */
    public Request() {
    }

    /**
     *
     */
    private PickupAddress pickup;


    private DeliveryAddress delivery;
    public Request(PickupAddress pickupAddress,DeliveryAddress deliveryAddress){
        this.pickup=pickupAddress;
        this.delivery=deliveryAddress;
    }

}