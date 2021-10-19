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


    public Double getLatitude() {
        return latitude;
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
}