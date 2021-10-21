package model;

import java.util.*;

/**
 * @author 4IF-4114
 */
public class PointOfInterest {
    protected Integer duration;
    protected Intersection intersection;
    /**
     * Default constructor
     */
    public PointOfInterest() {
    }

    public Intersection getIntersection() {
        return intersection;
    }

    /**
     * 
     */


    public PointOfInterest(Intersection i) {
        this.intersection=i;
    }

}