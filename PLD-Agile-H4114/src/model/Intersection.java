package model;

import java.util.*;

/**
 * @author 4IF-4114
 */
public class Intersection {

    protected String id;
    protected Double latitude;
    protected Double longitude;


    public Intersection() {
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



}