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
    private HashMap<Integer,Intersection> intersections;
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

    public void addIntersection(Intersection intersection) {
        this.intersections.put(intersection.id,intersection);
    }

    public void addRoad(Road road, int id1, int id2) {
        Intersection origin = this.intersections.get(id1);
        Intersection destination = this.intersections.get(id2);
        if(origin!=null && destination!=null){
            road.addRoads(origin,destination);
            this.roads.add(road);
        }
    }
}