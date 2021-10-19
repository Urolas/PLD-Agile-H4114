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
        if(obj.getClass()!=this.getClass()){
            return false;
        }
        if(!this.intersection.equals(((PointOfInterest) obj).intersection)){
            return false;
        }
        if(Integer.compare(this.duration,((PointOfInterest) obj).duration)!=0){
            return false;
        }
        return true;
    }
}