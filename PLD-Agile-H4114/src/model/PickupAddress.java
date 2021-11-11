/**
 * PickupAddress
 * @author 4IF-4114
 */
package model;


import java.util.Objects;

/**
 * The intersection (or a point of interest) where the deliveryman will pickup a package
 */
public class PickupAddress extends PointOfInterest {

    /**
     * Default constructor of PickupAddress
     */
    public PickupAddress() {
    }

    /**
     * Constructor of PickupAddress
     * @param pintersection the intersection of the pickup address
     * @param pickupDuration the duration the deliveryman can stay on this point
     * @param i the id of the point of interest
     */
    public PickupAddress(Intersection pintersection, Integer pickupDuration,Integer i) {
        super(pintersection,i);
        this.duration=pickupDuration;
    }

    /**
     * Compares this pickup address with another object and check if they are equal
     * @param obj the object to be compared with
     */
    @Override
    public boolean equals(Object obj) {
        if(!super.equals(obj)) {
            return false;
        }
        if(!Objects.equals(this.duration, ((PickupAddress) obj).duration)){
            return false;
        }
        return true;
    }
}