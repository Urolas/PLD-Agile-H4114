/**
 * Intersection
 *
 * @author 4IF-4114
 */
package model;

/**
 * An intersection is represented by its id, latitude and longtitude
 */
public class Intersection {

    protected String id;
    protected Double latitude;
    protected Double longitude;

    /**
     * Default constructor of an intersection
     */
    public Intersection() {
        this.id = "-1";
        this.longitude = 0.0;
        this.latitude = 0.0;
    }

    /**
     * Constructor of intersection
     * @param myId the id of the intersection
     * @param myLatitude the latitude of the intersection
     * @param myLongitude the longitude of the intersection
     */
    public Intersection(String myId, Double myLatitude, Double myLongitude) {
        this.id = myId;
        this.latitude = myLatitude;
        this.longitude = myLongitude;
    }

    /**
     * Compares this intersection with another object and check if they are equal
     * @param o the object to be compared with
     */
    @Override
    public boolean equals(Object o) {
        //Meme class
        if (!o.getClass().equals(this.getClass())) {
            return false;
        }
        //Meme id
        if (!this.id.equals(((Intersection) o).id)) {
            return false;
        }
        //Meme latitude
        if (Double.compare(this.latitude, ((Intersection) o).latitude) != 0) {
            return false;
        }
        //Meme longitude
        return Double.compare(this.longitude, ((Intersection) o).longitude) == 0;
    }


    @Override
    public String toString() {
        return "Intersection{" +
                "id='" + id + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public String getId() {
        return id;
    }
}