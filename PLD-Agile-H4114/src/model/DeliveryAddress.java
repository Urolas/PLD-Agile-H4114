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
}