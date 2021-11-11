/**
 * TSPDoubleInsertion
 *
 * @author 4IF-4114
 */
package tsp;

import model.GraphPointToPoint;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;

/**
 * A solution to the PDTSP inspired by the research paper
 * "A heuristic for the pickup and delivery traveling salesman problem"
 * by Jacques Ranaud, Fayez F. Boctor and Jamal Ouenniche
 * To be noted: The paper offer a full implementation to the PDTSP problem
 * During testing, we noticed that by implementing only steps 0,1 and 2
 * from the part 4.1 The Double Insertion heuristic, we found optimal solution to our test sets
 * We decided to not implement further and instead focus on other functionalities
 * If in the future, the number of daily requests to be computed were higher, further implementation may be required
 */
public class TSPDoubleInsertion implements TSP {
    protected List<Integer> bestSolution = new ArrayList<>();  //de base que g en protected, le reste en private
    protected GraphPointToPoint g;
    protected Set<Integer> pickupCandidate;
    private int timeLimit;
    private long startTime;

    /**
     * Construct the solution
     * @param timeLimit the execution time limit of the TSP
     * @param g the graph between every PointOfInterest
     */
    @Override
    public void searchSolution(int timeLimit, GraphPointToPoint g) {
        if (timeLimit <= 0) return;
        startTime = System.currentTimeMillis();
        this.timeLimit = timeLimit;
        this.g = g;
        pickupCandidate = g.getPickupSet();
        initialize();
        while(pickupCandidate.size()>0){
            minWeightedInsertionCost();
        }
    }

    /**
     * Insert the first request in the tour
     * The inserted request is the one maximizing the distance from the depot point
     */
    public void initialize(){
        /*initialize variables*/
        bestSolution.add(0);
        double maxCost = 0.0;
        int maxId = -1;

        /*for each request find the one that maximise insertion cost*/
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

    /**
     * Insert the request minimizing the overall insertion cost
     */
    public void minWeightedInsertionCost(){
        /*initialize variable*/
        double minCost = Double.POSITIVE_INFINITY;
        int minId = -1;
        double alpha = 1.0;
        int idInsertionPickup = -1;
        int idInsertionDelivery = -1;

        /*Look at each request*/
        for(Integer pickupPoint : pickupCandidate){

            /*Find the minimal insertion cost if the pickup and delivery point are adjacent*/
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

            /*Find the minimal insertion cost if the pickup and delivery are not adjacent*/
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

    /**
     * Calculate the total cost of the solution
     * @return the cost of the solution (length of the tour)
     */
    private Double calculateCost(){
        return calculateCost(bestSolution);
    }

    /**
     * Calculate the cost of a list of points
     * @param listPoint the list of point
     * @return the cost of the list of point (length between the points)
     */
    private Double calculateCost(List<Integer> listPoint){
        Double cost=0.0;
        for(int i=0;i<listPoint.size()-1;++i){
            cost+=g.getCost(listPoint.get(i),listPoint.get(i+1));
        }
        return cost;
    }

    /**
     * Give the number of point in the best solution
     * @return the number of points in the best solution
     */
    @Override
    public int getBestSolLength(){
        return bestSolution.size();
    }

    /**
     * Get a specific point id in the solution
     * @param i the id of the point
     * @return the element at the ith position in the best solution
     */
    @Override
    public Integer getSolution(int i){
        if (g != null && i>=0 && i<g.getNbVertices()+1)
            return bestSolution.get(i);
        return -1;
    }

    /**
     * Return the best solution cost
     * @return the solution cost
     */
    @Override
    public Double getSolutionCost(){
        if (g != null)
            return calculateCost();
        return -1.0;
    }

}
