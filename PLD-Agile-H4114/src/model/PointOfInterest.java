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

    /**
     * 
     */


    public PointOfInterest(Intersection i) {
        this.intersection=i;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass()!=PointOfInterest.class){
            return false;
        }
        if(!intersection.equals(((PointOfInterest) obj).intersection)){
            return false;
        }
        if(duration!=((PointOfInterest) obj).duration){
            return false;
        }
        return true;
    }
}