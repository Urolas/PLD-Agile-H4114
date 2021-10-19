package model;

import java.util.*;

import tsp.TSP;
import tsp.TSP1;
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

    //compute les meilleurs chemins entre chaque points d'interet, appelle le TSP pour trouver le meilleurs tour, créé le tour
    public void computeTour() {
        List<String> points = this.distribution.GetAllPoints();
        List<AbstractMap.SimpleEntry<String,String>> constraints = this.distribution.GetConstraints();
        //TODO si possible changer HashMap<String,HashMap<String,AbstractMap.SimpleEntry<Double,List<String>>>> par une formule digeste, au moins pour la lecture
        HashMap<String,HashMap<String,AbstractMap.SimpleEntry<Double,List<String>>>> ResultsDijkstra = new HashMap<>();
        for (String source : points){
            HashMap<String, AbstractMap.SimpleEntry<Double,List<String>>> distanceToOtherPoints = new HashMap<>();
            for (String target : points){
                if (!Objects.equals(target, source)) {
                    distanceToOtherPoints.put(target,this.computePath(source,target));
                }
            }
            ResultsDijkstra.put(source,distanceToOtherPoints);
        }
        GraphPointToPoint graph =new GraphPointToPoint(ResultsDijkstra,points,constraints);
        /** TODO Impossible a faire sans un dijkstra censé, a debugé apres
        TSP tsp = new TSP1();
        tsp.searchSolution(2000,graph);
        List<String> shortestTour = new ArrayList<>();
        shortestTour.add(points.get(0));
        List<String> shortestPath = new ArrayList<>();
        for (int i=1;i<tsp.getSolutionCost();i++) {
            shortestTour.add(tsp.getSolution(i));
            shortestPath.addAll(ResultsDijkstra.get(shortestTour.get(i-1)).get(shortestTour.get(i)).getValue());
        }
        TODO create tour with shortestPath and shortestTour : how to use the path object ?
         **/
    }
    //computePath execute l'algorithme de dijkstra et cherche le meilleur chemin entre deux intersections, il renvoit le chemin et sa longueur
    public AbstractMap.SimpleEntry<Double,List<String>> computePath(String intersection1, String intersection2) {
        //TODO faire le dijkstra
        return new AbstractMap.SimpleEntry<>(1.0,new ArrayList<>());
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