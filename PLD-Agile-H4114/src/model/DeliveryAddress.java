package model;

import java.util.*;

/**
 * @author 4IF-4114
 */
public class DeliveryAddress extends PointOfInterest {

    /**
     * Default constructor
     */
    public DeliveryAddress() {
    }

    public DeliveryAddress(Intersection dintersection, Integer deliveryDuration) {
        super(dintersection);
        this.duration=deliveryDuration;
    }

    @Override
    public boolean equals(Object obj) {
        if(!super.equals(obj)) {
            return false;
        }
        if(DeliveryAddress.class!=obj.getClass()){
            return false;
        }
        return true;
    }
}