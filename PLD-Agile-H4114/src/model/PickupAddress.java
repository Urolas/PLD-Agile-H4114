package model;


import java.util.Objects;

/**
 * @author 4IF-4114
 */
public class PickupAddress extends PointOfInterest {

    /**
     * Default constructor
     */
    public PickupAddress() {
    }

    public PickupAddress(Intersection pintersection, Integer pickupDuration,Integer i) {
        super(pintersection,i);
        this.duration=pickupDuration;
    }


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