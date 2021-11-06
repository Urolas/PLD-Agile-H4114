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

    public boolean contains(double clickLong, double clickLat) {
        double lon = this.intersection.getLongitude();
        double lat = this.intersection.getLatitude();

        double x = (lat - clickLat) * Math.cos((lon + clickLong) / 2);
        double y = lon - clickLong;
        double z = Math.sqrt(x*x + y*y);
        double k = 1.852 * 60;
        double d = k * z;

        System.out.println("PointOfInterest/contains:");
        System.out.println("lon :" + lon);
        System.out.println("lat :" + lat);
        System.out.println("clickLong :" + clickLong);
        System.out.println("clickLat :" + clickLat);
        System.out.println("d :" + d);

        if (d < 0.001) return true;
        return false;
    }
}