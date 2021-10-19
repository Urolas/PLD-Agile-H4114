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
        if (o.getClass() != Intersection.class) {
            return false;
        }
        //Meme id
        if(id != ((Intersection) o).id){
            return false;
        }
        //Meme latitude
        if(latitude!=((Intersection) o).latitude){
            return false;
        }
        //Meme longitude
        if(longitude!=((Intersection) o).longitude){
            return false;
        }
        return true;
    }



}