package tsp;

import model.GraphPointToPoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class TSPDoubleInsertion implements TSP {
    protected List<Integer> bestSol;  //de base que g en protected, le reste en private
    protected GraphPointToPoint g;
    protected Double bestSolCost;
    private int timeLimit;
    private long startTime;

    @Override
    public void searchSolution(int timeLimit, GraphPointToPoint g) {
        if (timeLimit <= 0) return;
        startTime = System.currentTimeMillis();
        this.timeLimit = timeLimit;
        this.g = g;
        bestSol = new ArrayList(g.getNbVertices()+1);
        initialize();
        System.err.println("Double Insertion");

    }

    @Override
    public Integer getSolution(int i){
        if (g != null && i>=0 && i<g.getNbVertices()+1)
            return bestSol.get(i);
        return -1;
    }

    @Override
    public Double getSolutionCost(){
        if (g != null)
            return bestSolCost;
        return -1.0;
    }

    public void initialize(){
        bestSol.add(0);
        double maxCost = 0.0;
        int maxId = -1;
        for(Integer pId : g.getPickupSet()){
            Integer dId = g.getDelivery(pId);

            double cost = g.getCost(0,pId)+g.getCost(pId,dId)+g.getCost(dId,0);

            if(cost > maxCost){
                maxId = pId;
                maxCost = cost;
            }
        }
        bestSol.add(maxId);
        bestSol.add(g.getDelivery(maxId));
        bestSol.add(0);

    }


}
