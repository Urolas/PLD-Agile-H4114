package model;


import java.util.Objects;

/**
 * @author 4IF-4114
 */
public class PointOfInterest {

    protected Integer duration;
    protected Intersection intersection;
    protected Integer idPointOfInterest;

    /**
     * Default constructor
     */
    public PointOfInterest() {
        this.duration=-1;
        this.intersection=new Intersection();
        this.idPointOfInterest=-1;
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