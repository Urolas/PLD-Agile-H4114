package model;

import java.util.*;

/**
 * @author 4IF-4114
 */
public class PickupAddress extends PointOfInterest {

    /**
     * Default constructor
     */
    public PickupAddress() {
    }

    public PickupAddress(Intersection pintersection, Integer pickupDuration) {
        super(pintersection);
        this.duration=pickupDuration;
    }

    @Override
    public boolean equals(Object obj) {
        if(!super.equals(obj)) {
            return false;
        }
        if(PickupAddress.class!=obj.getClass()){
            return false;
        }
        return true;
    }
}