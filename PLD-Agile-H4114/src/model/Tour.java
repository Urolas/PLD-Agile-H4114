package model;

import java.util.*;

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
    }

    public Tour(List<Path> paths, List<PointOfInterest> shortestTour, Double solutionCost) {
        this.paths=paths;
        this.pointOfInterests=shortestTour;
        this.totalLength=solutionCost;
    }
}

