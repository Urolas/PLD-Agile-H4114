package model;


/**
 * @author 4IF-4114
 */
public class Road {

    private Double length;
    private String name;
    private Intersection origin;
    private Intersection destination;

    @Override
    public String toString() {
        return "Road{" +
                "length=" + length +
                ", name='" + name + '\'' +
                ", origin=" + origin +
                ", destination=" + destination +
                '}';
    }

    public Intersection getOrigin() {
        return origin;
    }

    public Intersection getDestination() {
        return destination;
    }

    /**
     * Default constructor
     */
    public Road() {


    }

    public String getName() {
        return name;
    }

    public Road(String myName, double myLength) {
        this.length = myLength;
        this.name = myName;
    }


    public void addRoads(Intersection myOrigin,Intersection myDestination){
        this.origin=myOrigin;
        this.destination=myDestination;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != Road.class) {
            return false;
        }
        //Meme path
        if (Double.compare(this.length,((Road) obj).length)!=0) {
            return false;
        }
        //Meme pointOfInterests
        if (this.name!=((Road) obj).name) {
            return false;
        }
        if (!this.origin.equals(((Road) obj).origin)) {
            return false;
        }
        if (!this.destination.equals(((Road) obj).destination)) {
            return false;
        }
        return true;
    }

    /**
     * 
     */

    public Double getLength() {
        return length;
    }
}