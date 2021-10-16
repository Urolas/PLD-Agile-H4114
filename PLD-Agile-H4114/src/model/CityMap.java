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
    private Set<Road> roads;
    private Set<Intersection> intersections;
    public Distribution distribution;
    public Tour tour;
    private Double width,height;
    /**
     * @param intersection1 
     * @param intersection2 
     * @return
     */
    public void computeTour(Intersection intersection1, Intersection intersection2) {
        // TODO implement here
    }

    public void reset(Double width,Double height){
        this.distribution = new Distribution();
        this.tour = new Tour();
        this.intersections.clear();
        this.roads.clear();
        this.width=width;
        this.height=height;
    }

}