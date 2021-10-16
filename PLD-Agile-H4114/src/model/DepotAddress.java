package model;

import java.time.LocalTime;
import java.util.*;

/**
 * @author 4IF-4114
 */
public class DepotAddress extends PointOfInterest {

    /**
     * Default constructor
     */
    public DepotAddress() {
    }

    /**
     * 
     */
    private String departureTime;  // TODO change String to a Time format


    public DepotAddress(Intersection i, String departureTime) {
        super(i);
        this.departureTime=departureTime;
    }
}