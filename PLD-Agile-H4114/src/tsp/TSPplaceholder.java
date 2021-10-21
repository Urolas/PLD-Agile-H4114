package tsp;

import model.GraphPointToPoint;

import java.util.ArrayList;
import java.util.Collection;

public class TSPplaceholder extends TSP1{
    public void searchSolution(int timeLimit, GraphPointToPoint g){
        this.g=g;
        bestSol = new Integer[g.getNbVertices()+1];
        int i;
        for(i=0;i<bestSol.length-1;i++){
            bestSol[i]=i;
        }
        bestSol[i]=0;
        bestSolCost = (double) g.getNbVertices()+1;
    }
}
