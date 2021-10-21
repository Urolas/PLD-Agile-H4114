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

    public DeliveryAddress(Intersection dintersection, Integer deliveryDuration,Integer i) {
        super(dintersection,i);
        this.duration=deliveryDuration;
    }
}