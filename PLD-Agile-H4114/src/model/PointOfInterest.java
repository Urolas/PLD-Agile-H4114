package model;

import java.util.*;

/**
 * @author 4IF-4114
 */
public class PointOfInterest {

    /**
     * Default constructor
     */
    public PointOfInterest() {
    }

    /**
     * 
     */
    protected Integer duration;
    protected Intersection intersection;

    public PointOfInterest(Intersection i) {
        this.intersection=i;
    }
}