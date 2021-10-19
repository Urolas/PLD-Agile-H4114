package model;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphPointToPoint {
    private int nbVertices;
    private HashMap<String, HashMap<String, AbstractMap.SimpleEntry<Double,List<String>>>> cost;
    public GraphPointToPoint(HashMap<String, HashMap<String, AbstractMap.SimpleEntry<Double,List<String>>>> resultsDijkstra) {
        nbVertices=resultsDijkstra.size();
        this.cost=resultsDijkstra;

    }

    public int getNbVertices() {
        return nbVertices;
    }

    public Double getCost(String i1, String i2) {

        return this.cost.get(i1).get(i2).getKey();
    }

}
