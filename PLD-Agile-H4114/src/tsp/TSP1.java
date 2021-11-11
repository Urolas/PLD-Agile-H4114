package tsp;

import model.GraphPointToPoint;

import java.util.Collection;
import java.util.Iterator;

/**
 * The first version of the TSP implementation
 * This version uses a Branch and Bound algorithm
 * The euristic used in this version (bound) is to simply return 0, so we obtain really poor performances
 */
public class TSP1 extends TemplateTSP {

    /**
     * Return, a lower bound of the length of the path still needed to complete the TSP
     * @param currentVertex the current vertex
     * @param unvisited the set of all the vertex not yet visited
     */
    @Override
    protected int bound(Integer currentVertex, Collection<Integer> unvisited) {
        return 0;
    }

    @Override
    protected Iterator<Integer> iterator(Integer currentVertex, Collection<Integer> unvisited, GraphPointToPoint g) {
        return new SeqIter(unvisited);
    }

    @Override
    public int getBestSolLength() {
        return 0;
    }
}
