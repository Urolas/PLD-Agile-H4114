package model;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 4IF-4114
 */
public class Tour {
    private List<Path> paths;
    public List<PointOfInterest> pointOfInterests;
    private Double totalLength;
    /**
     * Default constructor
     */
    public Tour() {
        paths = new HashSet<>();
        pointOfInterests = new HashSet<>();
    }


    public Tour(List<Path> paths, List<PointOfInterest> shortestTour, Double solutionCost) {
        this.paths=paths;
        this.pointOfInterests=shortestTour;
        this.totalLength=solutionCost;
    }



    @Override
    //Methode d'egalité entre les Tours
    public boolean equals(Object o) {
        //Meme class
        if (o.getClass() != Tour.class) {
            return false;
        }
        //Meme path
        if (!this.paths.equals(((Tour) o).paths)) {
            return false;
        }
        //Meme pointOfInterests
        if (!this.pointOfInterests.equals(((Tour) o).pointOfInterests)) {
            return false;
        }
        return true;


    }
}

