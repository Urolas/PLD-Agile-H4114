/**
 * Tour
 * @author 4IF-4114
 */
package model;

import observer.Observable;
import java.util.ArrayList;
import java.util.List;

/**
 * Tour contains a list of path and point of interest, in order, representing the path after computing a tour
 */
public class Tour extends Observable {
    private List<Path> paths;
    private List<PointOfInterest> pointOfInterests;
    private Double totalLength;
    /**
     * Default constructor of Tour
     */
    public Tour() {
        paths = new ArrayList<>();
        pointOfInterests = new ArrayList<>();
    }
    /**
     * Empty the tour
     */
    public void resetTour(){
        paths = new ArrayList<>();
        pointOfInterests = new ArrayList<>();
        this.notifyObservers();
    }

    /**
     * Compares this tour with another object and check if they are equal
     * @param obj the object to be compared with
     */
    @Override
    public boolean equals(Object obj) {
        //Same class
        if (!obj.getClass().equals(Tour.class)) {
            return false;
        }
        //Same path
        if (!this.paths.equals(((Tour) obj).paths)) {
            return false;
        }
        //Same pointOfInterests
        if (!this.pointOfInterests.equals(((Tour) obj).pointOfInterests)) {
            return false;
        }
        return true;


    }

    /**
     * From the tour, find the point located just before the given point of interest
     * @param pointOfInterest the given point of interest
     * @return the point of interest located before the given point
     */
    public PointOfInterest getPointBefore ( PointOfInterest pointOfInterest){
        return this.pointOfInterests.get(this.pointOfInterests.indexOf(pointOfInterest)-1);

    }

    public Double getTotalLength() {
        return totalLength;
    }

    public List<Path> getPaths() {return paths;}

    public List<PointOfInterest> getPointOfInterests() {return pointOfInterests;}

    public void setPaths(List<Path> paths) {this.paths = paths;}

    public void setPointOfInterests(List<PointOfInterest> pointOfInterests) {
        this.pointOfInterests = pointOfInterests;
    }

    public void setTotalLength(Double totalLength) {
        this.totalLength = totalLength;
        this.notifyObservers(totalLength);
    }
}

