package model;

import observer.Observable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 4IF-4114
 */
public class Tour extends Observable {
    private List<Path> paths;
    public List<PointOfInterest> pointOfInterests;
    private Double totalLength;
    /**
     * Default constructor
     */
    public Tour() {
        paths = new ArrayList<>();
        pointOfInterests = new ArrayList<>();
    }

    public void resetTour(){
        paths = new ArrayList<>();
        pointOfInterests = new ArrayList<>();
    }

    public List<Path> getPaths() {
        return paths;
    }

    public List<PointOfInterest> getPointOfInterests() {
        return pointOfInterests;
    }

    public Tour(List<Path> paths, List<PointOfInterest> shortestTour, Double solutionCost) {
        this.paths=paths;
        this.pointOfInterests=shortestTour;
        this.totalLength=solutionCost;
    }

    public void setPaths(List<Path> paths) {
        this.paths = paths;
    }

    public void setPointOfInterests(List<PointOfInterest> pointOfInterests) {
        this.pointOfInterests = pointOfInterests;
    }

    public void setTotalLength(Double totalLength) {
        this.totalLength = totalLength;
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

