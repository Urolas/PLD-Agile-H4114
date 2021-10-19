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
    private HashMap<String,List<AbstractMap.Entry<String,Double>>> adjacencyList;


    /**
     *
     * Default constructor
     */
    public CityMap() {
        this.intersections= new HashMap<>();
        this.roads= new HashSet<>();
        this.adjacencyList= new HashMap<>();

    }

    /**
     * @param intersection1 
     * @param intersection2 
     * @return
     */
    public void computePath(Intersection intersection1, Intersection intersection2) {
        // TODO implement here
    }
    public void computeTour() {
        List<String> points = this.distribution.GetAllPoints();
        HashMap<String,HashMap<String,AbstractMap.SimpleEntry<Double,List<String>>>> ResultsDijkstra = new HashMap<>();
        for (String wantedIntersection : points){
            ResultsDijkstra.put(wantedIntersection,Dijkstra(wantedIntersection,points));
        }
        GraphPointToPoint graph =new GraphPointToPoint(ResultsDijkstra);
        graph.getNbVertices();
    }

    private HashMap<String, AbstractMap.SimpleEntry<Double,List<String>>> Dijkstra(String source, List<String> points) {
        HashMap<String, AbstractMap.SimpleEntry<Double,List<String>>> result = new HashMap<>();
        for (String intersection : points){
            if (intersection!=source) {
                result.put(intersection, new AbstractMap.SimpleEntry<>(1.0,new ArrayList<>()));
            }
        }
        return result;
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

    public void addRoad(String name, Double length, String id1, String id2) {
        Intersection origin = this.intersections.get(id1);
        Intersection destination = this.intersections.get(id2);
        Road road = new Road(name,length);
        if(origin!=null && destination!=null){
            road.addRoads(origin,destination);
            this.roads.add(road);
        }
    }

    public void completeAdjacencyList(String id1, String id2, Double length) {
        if (this.adjacencyList.get(id1)==null){
            List<AbstractMap.Entry<String,Double>> targets= new ArrayList<>();
            targets.add(new AbstractMap.SimpleEntry<>(id2,length));
            this.adjacencyList.put(id1,targets);
        } else {
            this.adjacencyList.get(id1).add(new AbstractMap.SimpleEntry<>(id2,length));
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