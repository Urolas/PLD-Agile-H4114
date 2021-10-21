package tsp;

import model.GraphPointToPoint;

import java.util.ArrayList;
import java.util.Collection;

public class TSPplaceholder extends TSP1{
    public void searchSolution(int timeLimit, GraphPointToPoint g){
        this.g=g;
        bestSol = new Integer[g.getNbVertices()];
        for(int i=0;i<bestSol.length;i++){
            bestSol[i]=i;
        }
        bestSolCost = (double) g.getNbVertices();
    }
}
