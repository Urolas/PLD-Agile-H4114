package model;

import view.MapView;
import observer.Observable;
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

    @Override
    public String toString() {
        return "CityMap{" +
                "roads=" + roads+
                '}';
    }

    /**
     * Default constructor
     */
    public CityMap() {
        this.intersections= new HashMap<String,Intersection>();
        this.roads= new HashSet<Road>();
        this.width = 0.;
        this.height = 0.;
        this.nordPoint = 0.;
        this.westPoint = 0.;
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
        notifyObservers();
    }

    public void addIntersection(Intersection intersection) {
        this.intersections.put(intersection.id,intersection);
        notifyObservers(intersection);
    }

    public void addRoad(Road road, String id1, String id2) {
        Intersection origin = this.intersections.get(id1);
        Intersection destination = this.intersections.get(id2);
        if(origin!=null && destination!=null){
            road.addRoads(origin,destination);
            this.roads.add(road);
        }
        notifyObservers(road);
    }
    public HashMap<String,Intersection>getIntersections(){
        return this.intersections;
    }

    public void addObserver(MapView mapView) {
        super.addObserver(mapView);
    }

    public Distribution getDistribution() {
        return distribution;
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