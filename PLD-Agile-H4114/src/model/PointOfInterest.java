package model;


import observer.Observable;

import java.awt.*;
import observer.Observable;

/**
 * @author 4IF-4114
 */
public class PointOfInterest extends Observable {

    protected Integer duration;
    protected Intersection intersection;
    protected Integer idPointOfInterest;
    private boolean isSelected;

    /**
     * Default constructor
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
     * 
     */


    public PointOfInterest(Intersection i,Integer idPoint) {
        this.idPointOfInterest=idPoint;
        this.intersection=i;

    }

    public Integer getDuration() {
        return duration;
    }

    public Integer getIdPointOfInterest() {
        return idPointOfInterest;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass()!=this.getClass()){
            return false;
        }
        if(!this.intersection.equals(((PointOfInterest) obj).intersection)){
            return false;
        }
        if(Integer.compare(this.duration,((PointOfInterest) obj).duration)!=0){
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