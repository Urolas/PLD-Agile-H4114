package model;



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
}