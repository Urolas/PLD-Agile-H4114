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

    public PickupAddress(Intersection pintersection, Integer pickupDuration,Integer i) {
        super(pintersection,i);
        this.duration=pickupDuration;
    }
}