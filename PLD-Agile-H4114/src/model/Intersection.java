package model;



/**
 * @author 4IF-4114
 */
public class Intersection {

    protected String id;
    protected Double latitude;
    protected Double longitude;


    public Intersection() {
        this.id="-1";
        this.longitude=0.0;
        this.latitude=0.0;
    }

    public Intersection(String myId,Double myLatitude,Double myLongitude){
        this.id = myId;
        this.latitude = myLatitude;
        this.longitude = myLongitude;
    }

    @Override
    public boolean equals(Object o){
        //Meme class
        if (o.getClass() != this.getClass()) {
            return false;
        }
        //Meme id
        if(this.id != ((Intersection) o).id){
            return false;
        }
        //Meme latitude
        if(Double.compare(this.latitude,((Intersection) o).latitude)!=0){
            return false;
        }
        //Meme longitude
        if(Double.compare(this.longitude,((Intersection) o).longitude)!=0){
            return false;
        }
        return true;
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
    public String getId() {return id;}
}