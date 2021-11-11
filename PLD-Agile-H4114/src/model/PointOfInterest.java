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
    private boolean isSelected;

    /**
     * Default constructor of PointOfInterest
     */
    public PointOfInterest() {
        this.duration=-1;
        this.intersection=new Intersection();
        this.idPointOfInterest=-1;
        this.isSelected = false;
    }

    public boolean getIsSelected(){
        return isSelected;
    }

    public void setIsSelected(boolean b) {
        isSelected = b;
        notifyObservers(this);
    }

    public Intersection getIntersection() {
        return intersection;
    }

    /**
     * Constructor of PointOfInterest
     * @param i the intersection of the pointOfInterest
     * @param idPoint the id of the point
     */
    public PointOfInterest(Intersection i,Integer idPoint) {
        this.idPointOfInterest=idPoint;
        this.intersection=i;

    }

    public Color getColor() { return color; }

    public Integer getDuration() {
        return duration;
    }

    public Integer getIdPointOfInterest() {
        return idPointOfInterest;
    }

    /**
     * Compares this point of interest with another object and check if they are equal
     * @param obj the object to be compared with
     */
    @Override
    public boolean equals(Object obj) {
        if(obj.getClass()!=this.getClass()){
            return false;
        }
        if(!this.intersection.equals(((PointOfInterest) obj).intersection)){
            return false;
        }
        if(!Objects.equals(this.duration, ((PointOfInterest) obj).duration)){
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        return "PointOfInterest{" +
                "duration=" + duration +
                ", intersection=" + intersection +
                ", idPointOfInterest=" + idPointOfInterest +
                '}';
    }
}