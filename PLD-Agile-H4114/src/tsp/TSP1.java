package tsp;

import model.GraphPointToPoint;

import java.util.Collection;
import java.util.Iterator;

public class TSP1 extends TemplateTSP {
    @Override
    protected int bound(String currentVertex, Collection<String> unvisited) {
        return 0;
    }

    @Override
    protected Iterator<String> iterator(String currentVertex, Collection<String> unvisited, GraphPointToPoint g) {
        return new SeqIter(unvisited, currentVertex, g);
    }

}
