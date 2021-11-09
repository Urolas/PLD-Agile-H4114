package tsp;

import model.GraphPointToPoint;

import java.util.*;

public class TSPDoubleInsertion implements TSP {
    protected List<Integer> bestSol;  //de base que g en protected, le reste en private
    protected GraphPointToPoint g;
    protected Double bestSolCost;
    protected Set<Integer> pickupCandidate;
    private int timeLimit;
    private long startTime;

    @Override
    public void searchSolution(int timeLimit, GraphPointToPoint g) {
        if (timeLimit <= 0) return;
        startTime = System.currentTimeMillis();
        this.timeLimit = timeLimit;
        this.g = g;
        bestSol = new ArrayList(g.getNbVertices()+1);
        pickupCandidate = g.getPickupSet();
        initialize();
        while(pickupCandidate.size()>0){
            minWeightedInsertionCost();
        }

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
            return calculateCost();
        return -1.0;
    }

    private Double calculateCost(){
        return calculateCost(bestSol);
    }

    private Double calculateCost(List<Integer> listPoint){
        Double cost=0.0;
        for(int i=0;i<listPoint.size()-1;++i){
            cost+=g.getCost(listPoint.get(i),listPoint.get(i+1));
        }
        return cost;
    }

    //Insert la premiere requete dans le tour
    //La requete inserée est celle maximisant la distance par rapport au depot
    public void initialize(){
        bestSol.add(0);
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
        bestSol.add(maxId);
        bestSol.add(g.getDelivery(maxId));
        pickupCandidate.remove(maxId);
        bestSol.add(0);

    }

    // Insere la requete minimisant le cout d'insertion global
    public void minWeightedInsertionCost(){
        double minCost = Double.POSITIVE_INFINITY;
        int minId = -1;
        double alpha = 1.0;
        int idInsertionPickup = -1;
        int idInsertionDelivery = -1;
        for(Integer pickupPoint : pickupCandidate){


            for(int k=0;k<bestSol.size()-1;++k){
                double cost =   alpha*g.getCost(bestSol.get(k),pickupPoint)+g.getCost(pickupPoint,g.getDelivery(pickupPoint))
                        +(2-alpha)*g.getCost(g.getDelivery(pickupPoint),bestSol.get(k+1))-g.getCost(bestSol.get(k),bestSol.get(k+1));
                if (cost<minCost){
                    minCost = cost;
                    idInsertionPickup = k;
                    idInsertionDelivery = k+1;
                    minId=pickupPoint;
                }
            }




            int secondMinId = -1;
            for(int k=0;k<bestSol.size()-2;++k){

                 for (int s=k+1;s<bestSol.size()-1;++s){
                     double cost = alpha*(g.getCost(bestSol.get(k),pickupPoint)+g.getCost(pickupPoint,bestSol.get(k+1))-g.getCost(bestSol.get(k),bestSol.get(k+1)))
                             +(2-alpha)*(g.getCost(bestSol.get(s),g.getDelivery(pickupPoint))+g.getCost(g.getDelivery(pickupPoint),bestSol.get(s+1))-g.getCost(bestSol.get(s),bestSol.get(s+1)));

                     if(cost<minCost){
                         minCost=cost;
                         idInsertionPickup = k;
                         idInsertionDelivery = s+1;
                         minId=pickupPoint;
                     }
                 }
            }

        }
        bestSol.add(idInsertionPickup+1,minId);
        bestSol.add(idInsertionDelivery+1,g.getDelivery(minId));
        pickupCandidate.remove(minId);


    }

    @Override
    public int getBestSolLength(){
        return bestSol.size();
    }


}
