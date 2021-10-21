package model;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphPointToPoint {
    private int nbVertices;
    private HashMap<Integer, HashMap<Integer, AbstractMap.SimpleEntry<Double,List<String>>>> cost;
    private List<AbstractMap.SimpleEntry<String,String>> constraints;


    public GraphPointToPoint(HashMap<PointOfInterest, HashMap<PointOfInterest, AbstractMap.SimpleEntry<Double,List<String>>>> resultsDijkstra, List<AbstractMap.SimpleEntry<String,String>> constraints) {
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

    public Double getCost(Integer i1, Integer i2) {

        return this.cost.get(i1).get(i2).getKey();
    }

}
