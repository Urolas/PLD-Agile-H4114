/**
 * DeliveryAddress
 * @author 4IF-4114
 */
package model;


import java.util.Objects;

/**
 * Point of interest as a delivery point
 */
public class DeliveryAddress extends PointOfInterest {

    /**
     * Default constructor of DeliveryAddress
     */
    public DeliveryAddress() {
    }

    /**
     * Constructor of DeliveryAddress
     * @param dintersection the intersection of this delivery address
     * @param deliveryDuration the duration the deliveryman can stay on this address
     * @param i the id of the point
     */
    public DeliveryAddress(Intersection dintersection, Integer deliveryDuration,Integer i) {
        super(dintersection,i);

        this.duration=deliveryDuration;
    }

    /**
     * Compares this delivery address with another object and check if they are equal
     * @param obj the object to be compared with
     */
    @Override
    public boolean equals(Object obj) {
        if(!super.equals(obj)) {
            return false;
        }
        if(!Objects.equals(this.duration, ((DeliveryAddress) obj).duration)){
            return false;
        }
        return true;
    }
}