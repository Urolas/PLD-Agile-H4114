/**
 * Road
 * @author 4IF-4114
 */
package model;

/**
 * A segment caracterized by its name, length and the start and end Intersection
 */
public class Road {

    private final Double length;
    private final String name;
    private Intersection origin;
    private Intersection destination;






    public String getName() {
        return name;
    }

    /**
     * Constructor of Road
     * @param myName the name of the road
     * @param myLength the length of the road
     */
    public Road(String myName, double myLength) {
        this.length = myLength;
        this.name = myName;
    }

    /**
     * Update the road's information by adding the start Intersection and the end Intersection
     * @param myOrigin the origin, or the start intersection of the road
     * @param myDestination the destination, or the end intersection of the road
     */
    public void addRoads(Intersection myOrigin,Intersection myDestination){
        this.origin=myOrigin;
        this.destination=myDestination;
    }

    /**
     * Compares this road with another object and check if they are equal
     * @param obj the object to be compared with
     */
    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != Road.class) {
            return false;
        }
        //Same path
        if (Double.compare(this.length,((Road) obj).length)!=0) {
            return false;
        }
        //Same pointOfInterests
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

    @Override
    public String toString() {
        return "Road{" +
                "length=" + length +
                ", name='" + name + '\'' +
                ", origin=" + origin +
                ", destination=" + destination +
                '}';
    }
    public Double getLength() {
        return length;
    }

    public Intersection getOrigin() {
        return origin;
    }

    public Intersection getDestination() {
        return destination;
    }
}