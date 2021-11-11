/**
 * GraphPointToPoint
 * @author 4IF-4114
 */
package model;

import java.util.*;

/**
 * The graph representing the map, having the each point as node and each road as branch
 */
public class GraphPointToPoint {
    private int nbVertices;
    private HashMap<Integer, HashMap<Integer, AbstractMap.SimpleEntry<Double,List<String>>>> cost;
    private HashMap<Integer,Integer> constraints;


    /**
     * Constructor of GraphPointToPoint
     * @param resultsDijkstra the result returned from the Dijstra function
     * @param constraints the constraints of the priority order of the points of interest
     */
    public GraphPointToPoint(HashMap<PointOfInterest, HashMap<PointOfInterest, AbstractMap.SimpleEntry<Double,List<String>>>> resultsDijkstra, HashMap<Integer,Integer> constraints) {
        this.nbVertices=resultsDijkstra.size();
        this.constraints=constraints;
        this.cost= new HashMap<>();
        for(Map.Entry<PointOfInterest, HashMap<PointOfInterest, AbstractMap.SimpleEntry<Double,List<String>>>> entry : resultsDijkstra.entrySet()){
            HashMap<Integer, AbstractMap.SimpleEntry<Double,List<String>>> currentEntry = new HashMap<>();
            for(Map.Entry<PointOfInterest, AbstractMap.SimpleEntry<Double,List<String>>> entry2 : entry.getValue().entrySet()){
                currentEntry.put(entry2.getKey().idPointOfInterest,entry2.getValue());
            }
            cost.put(entry.getKey().idPointOfInterest,currentEntry);
        }
    }

    public int getNbVertices() {
        return nbVertices;
    }

    /**
     * get the cost between two nodes of the graph
     * @param i1 the id of the first node
     * @param i2 the id of the second node
     */
    public Double getCost(Integer i1, Integer i2) {
        return this.cost.get(i1).get(i2).getKey();
    }

    public Integer getDelivery(Integer pickup){

        return constraints.get(pickup);
    }

    public Set<Integer> getPickupSet(){

        return constraints.keySet();
    }

}
