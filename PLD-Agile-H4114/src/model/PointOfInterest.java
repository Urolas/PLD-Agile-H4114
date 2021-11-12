/**
 * PointOfInterest
 * @author 4IF-4114
 */
package model;


import java.awt.*;
import observer.Observable;
import java.util.Objects;

/**
 * A pickup address or a delivery address or a depot address
 */
public class PointOfInterest extends Observable {

    protected Integer duration;
    protected Intersection intersection;
    protected Integer idPointOfInterest;
    protected Color color;

    /**
     * Default constructor of PointOfInterest
     */
    public PointOfInterest() {
        this.duration=-1;
        this.intersection=new Intersection();
        this.idPointOfInterest=-1;
    }

    /**
     * Constructor of PointOfInterest
     * @param intersection the intersection of the pointOfInterest
     * @param idPoint the id of the point
     */
    public PointOfInterest(Intersection intersection,Integer idPoint) {
        this.idPointOfInterest=idPoint;
        this.intersection=intersection;
    }


    /**
     * Compares this point of interest with another object and check if they are equal
     * @param obj the object to be compared with
     */
    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        if(!this.intersection.equals(((PointOfInterest) obj).intersection)){
            return false;
        }
        return Objects.equals(this.duration, ((PointOfInterest) obj).duration);
    }


    @Override
    public String toString() {
        return "PointOfInterest{" +
                "duration=" + duration +
                ", intersection=" + intersection +
                ", idPointOfInterest=" + idPointOfInterest +
                '}';
    }

    public Color getColor() { return color; }

    public Integer getDuration() {
        return duration;
    }

    public Integer getIdPointOfInterest() {
        return idPointOfInterest;
    }

    public Intersection getIntersection() {
        return intersection;
    }

}