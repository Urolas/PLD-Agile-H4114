package tsp;

import model.GraphPointToPoint;

import java.util.Collection;
import java.util.Iterator;

public class SeqIter implements Iterator<String> {
    private String[] candidates;
    private int nbCandidates;

    /**
     * Create an iterator to traverse the set of vertices in <code>unvisited</code>
     * which are successors of <code>currentVertex</code> in <code>g</code>
     * Vertices are traversed in the same order as in <code>unvisited</code>
     * @param unvisited
     * @param currentVertex
     * @param g
     */
    public SeqIter(Collection<String> unvisited, String currentVertex, GraphPointToPoint g){
        this.candidates = new String[unvisited.size()];
        for (String s : unvisited){

            candidates[nbCandidates++] = s;
        }
    }

    @Override
    public boolean hasNext() {
        return nbCandidates > 0;
    }

    @Override
    public String next() {
        nbCandidates--;
        return candidates[nbCandidates];
    }

    @Override
    public void remove() {}

}
