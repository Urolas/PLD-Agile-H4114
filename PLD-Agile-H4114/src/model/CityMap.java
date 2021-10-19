package model;

import java.util.*;

/**
 * @author 4IF-4114
 */
public class CityMap extends Observable {
    private Set<Road> roads;
    private HashMap<String,Intersection> intersections;
    public Distribution distribution;
    public Tour tour;
    private Double width,height,nordPoint,westPoint;
    /**
     * Default constructor
     */
    public CityMap() {
        this.intersections= new HashMap<String,Intersection>();
        this.roads= new HashSet<Road>();
    }

    /**
     * @param intersection1 
     * @param intersection2 
     * @return
     */
    public void computeTour(Intersection intersection1, Intersection intersection2) {
        // TODO implement here
    }

    public void reset(){
        this.distribution = new Distribution();
        this.tour = new Tour();
        this.intersections.clear();
        this.roads.clear();
    }

    public void addIntersection(Intersection intersection) {
        this.intersections.put(intersection.id,intersection);
    }

    public void addRoad(Road road, String id1, String id2) {
        Intersection origin = this.intersections.get(id1);
        Intersection destination = this.intersections.get(id2);
        if(origin!=null && destination!=null){
            road.addRoads(origin,destination);
            this.roads.add(road);
        }
    }
    public HashMap<String,Intersection>getIntersections(){
        return this.intersections;
    }

    public Set<Road> getRoads() {
        return roads;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public void setNordPoint(Double nordPoint) {
        this.nordPoint = nordPoint;
    }

    public void setWestPoint(Double westPoint) {
        this.westPoint = westPoint;
    }

    public Double getWidth() {
        return width;
    }

    public Double getHeight() {
        return height;
    }

    public Double getNordPoint() {
        return nordPoint;
    }

    public Double getWestPoint() {
        return westPoint;
    }   
}