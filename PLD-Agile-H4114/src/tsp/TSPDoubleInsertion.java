package tsp;

import model.GraphPointToPoint;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;

public class TSPDoubleInsertion implements TSP {
    protected List<Integer> bestSolution;  //de base que g en protected, le reste en private
    protected GraphPointToPoint g;
    protected Set<Integer> pickupCandidate;
    private int timeLimit;
    private long startTime;

    /**
     * Insert the first request in the tour
     * The inserted request is the one maximizing the distance from the deposit
     */
    public void initialize(){
        bestSolution.add(0);
        double maxCost = 0.0;
        int maxId = -1;
        for(Integer pId : pickupCandidate){
            Integer dId = g.getDelivery(pId);

            double cost = g.getCost(0,pId)+g.getCost(pId,dId)+g.getCost(dId,0);

            if(cost > maxCost){
                maxId = pId;
                maxCost = cost;
            }
        }
        bestSolution.add(maxId);
        bestSolution.add(g.getDelivery(maxId));
        pickupCandidate.remove(maxId);
        bestSolution.add(0);
    }

    @Override
    public void searchSolution(int timeLimit, GraphPointToPoint g) {
        if (timeLimit <= 0) return;
        startTime = System.currentTimeMillis();
        this.timeLimit = timeLimit;
        this.g = g;
        bestSolution = new ArrayList(g.getNbVertices()+1);
        pickupCandidate = g.getPickupSet();
        initialize();
        while(pickupCandidate.size()>0){
            minWeightedInsertionCost();
        }
    }

    /**
     * Insert the request minimizing the overall insertion cost
     */
    public void minWeightedInsertionCost(){
        double minCost = Double.POSITIVE_INFINITY;
        int minId = -1;
        double alpha = 1.0;
        int idInsertionPickup = -1;
        int idInsertionDelivery = -1;
        for(Integer pickupPoint : pickupCandidate){

            for(int k = 0; k< bestSolution.size()-1; ++k){
                double cost = alpha*g.getCost(bestSolution.get(k),pickupPoint)+g.getCost(pickupPoint,g.getDelivery(pickupPoint))
                        +(2-alpha)*g.getCost(g.getDelivery(pickupPoint), bestSolution.get(k+1))-g.getCost(bestSolution.get(k), bestSolution.get(k+1));
                if (cost<minCost){
                    minCost = cost;
                    idInsertionPickup = k;
                    idInsertionDelivery = k+1;
                    minId=pickupPoint;
                }
            }

            for(int k = 0; k< bestSolution.size()-2; ++k){

                 for (int s = k+1; s< bestSolution.size()-1; ++s){
                     double cost = alpha*(g.getCost(bestSolution.get(k),pickupPoint)+g.getCost(pickupPoint, bestSolution.get(k+1))-g.getCost(bestSolution.get(k), bestSolution.get(k+1)))
                             +(2-alpha)*(g.getCost(bestSolution.get(s),g.getDelivery(pickupPoint))+g.getCost(g.getDelivery(pickupPoint), bestSolution.get(s+1))-g.getCost(bestSolution.get(s), bestSolution.get(s+1)));

                     if(cost<minCost){
                         minCost=cost;
                         idInsertionPickup = k;
                         idInsertionDelivery = s+1;
                         minId=pickupPoint;
                     }
                 }
            }
        }
        bestSolution.add(idInsertionPickup+1,minId);
        bestSolution.add(idInsertionDelivery+1,g.getDelivery(minId));
        pickupCandidate.remove(minId);
    }

    private Double calculateCost(){
        return calculateCost(bestSolution);
    }

    private Double calculateCost(List<Integer> listPoint){
        Double cost=0.0;
        for(int i=0;i<listPoint.size()-1;++i){
            cost+=g.getCost(listPoint.get(i),listPoint.get(i+1));
        }
        return cost;
    }

    @Override
    public int getBestSolLength(){
        return bestSolution.size();
    }

    @Override
    public Integer getSolution(int i){
        if (g != null && i>=0 && i<g.getNbVertices()+1)
            return bestSolution.get(i);
        return -1;
    }

    @Override
    public Double getSolutionCost(){
        if (g != null)
            return calculateCost();
        return -1.0;
    }

}
