package tsp;


import model.GraphPointToPoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public abstract class TemplateTSP implements TSP {
    private String[] bestSol;
    protected GraphPointToPoint g;
    private Double bestSolCost;
    private int timeLimit;
    private long startTime;

    public void searchSolution(int timeLimit, GraphPointToPoint g){
        if (timeLimit <= 0) return;
        startTime = System.currentTimeMillis();
        this.timeLimit = timeLimit;
        this.g = g;
        bestSol = new String[g.getNbVertices()];
        Collection<String> unvisited = new ArrayList<String>(g.getNbVertices()-1);
        unvisited = g.getVertices();
        Collection<String> visited = new ArrayList<String>(g.getNbVertices());
        visited.add(g.getVertices().get(0)); // The first visited vertex is 0
        bestSolCost = Double.MAX_VALUE;
        branchAndBound(g.getVertices().get(0), unvisited, visited, 0.0);
    }

    public String getSolution(int i){
        if (g != null && i>=0 && i<g.getNbVertices())
            return bestSol[i];
        return null;
    }

    public Double getSolutionCost(){
        if (g != null)
            return bestSolCost;
        return -1.0;
    }

    /**
     * Method that must be defined in TemplateTSP subclasses
     * @param currentVertex
     * @param unvisited
     * @return a lower bound of the cost of paths in <code>g</code> starting from <code>currentVertex</code>, visiting
     * every vertex in <code>unvisited</code> exactly once, and returning back to vertex <code>0</code>.
     */
    protected abstract int bound(String currentVertex, Collection<String> unvisited);

    /**
     * Method that must be defined in TemplateTSP subclasses
     * @param currentVertex
     * @param unvisited
     * @param g
     * @return an iterator for visiting all vertices in <code>unvisited</code> which are successors of <code>currentVertex</code>
     */
    protected abstract Iterator<String> iterator(String currentVertex, Collection<String> unvisited, GraphPointToPoint g);

    /**
     * Template method of a branch and bound algorithm for solving the TSP in <code>g</code>.
     * @param currentVertex the last visited vertex
     * @param unvisited the set of vertex that have not yet been visited
     * @param visited the sequence of vertices that have been already visited (including currentVertex)
     * @param currentCost the cost of the path corresponding to <code>visited</code>
     */
    private void branchAndBound(String currentVertex, Collection<String> unvisited, Collection<String> visited, Double currentCost){
        if (System.currentTimeMillis() - startTime > timeLimit) return;
        if (unvisited.size() == 0){

            if (currentCost+g.getCost(currentVertex,g.getVertices().get(0)) < bestSolCost){
                visited.toArray(bestSol);
                bestSolCost = currentCost+g.getCost(currentVertex,g.getVertices().get(0));
            }

        } else if (currentCost+bound(currentVertex,unvisited) < bestSolCost){
            Iterator<String> it = iterator(currentVertex, unvisited, g);
            while (it.hasNext()){
                String nextVertex = it.next();
                visited.add(nextVertex);
                unvisited.remove(nextVertex);
                branchAndBound(nextVertex, unvisited, visited,
                        currentCost+g.getCost(currentVertex, nextVertex));
                visited.remove(nextVertex);
                unvisited.add(nextVertex);
            }
        }
    }

}
