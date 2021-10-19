package model;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphPointToPoint {
    private int nbVertices;
    private List<String> Vertices;
    private HashMap<String, HashMap<String, AbstractMap.SimpleEntry<Double,List<String>>>> cost;
    private List<AbstractMap.SimpleEntry<String,String>> constraints;

    public GraphPointToPoint(HashMap<String, HashMap<String, AbstractMap.SimpleEntry<Double,List<String>>>> resultsDijkstra,List<String> points,List<AbstractMap.SimpleEntry<String,String>> constraints) {
        this.nbVertices=resultsDijkstra.size();
        this.cost=resultsDijkstra;
        this.Vertices=points;
        this.constraints=constraints;
    }

    public int getNbVertices() {
        return nbVertices;
    }

    public Double getCost(String i1, String i2) {

        return this.cost.get(i1).get(i2).getKey();
    }

    public List<String> getVertices() {
        return Vertices;
    }
}
