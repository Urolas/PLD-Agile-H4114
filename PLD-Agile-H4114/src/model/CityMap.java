package model;

import java.util.*;

/**
 * @author 4IF-4114
 */
public class CityMap extends Observable {

    /**
     * Default constructor
     */
    public CityMap() {
    }

    /**
     * 
     */
    private Set<Road> roads;

    /**
     * 
     */
    private Set<Intersection> intersections;

    /**
     * 
     */
    public Distribution distribution;

    /**
     * 
     */
    public Tour tour;

    /**
     * @param intersection1 
     * @param intersection2 
     * @return
     */
    public void computeTour(Intersection intersection1, Intersection intersection2) {
        // TODO implement here
        return null;
    }

}