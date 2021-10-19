package model;

import java.util.*;

/**
 * @author 4IF-4114
 */
public class Road {

    private Double length;
    private String name;
    private Intersection origin;
    private Intersection destination;

    /**
     * Default constructor
     */
    public Road() {


    }

    public Road(String myName, double myLength) {
        this.length = myLength;
        this.name = myName;
    }


    public void addRoads(Intersection myOrigin,Intersection myDestination){
        this.origin=myOrigin;
        this.destination=myDestination;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != Road.class) {
            return false;
        }
        //Meme path
        if (length!=((Road) obj).length) {
            return false;
        }
        //Meme pointOfInterests
        if (name!=((Road) obj).name) {
            return false;
        }
        if (origin.equals(((Road) obj).origin)) {
            return false;
        }
        if (destination.equals(((Road) obj).destination)) {
            return false;
        }
        return true;
    }

    /**
     * 
     */


}