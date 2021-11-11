/**
 * DepotAddress
 * @author 4IF-4114
 */
package model;

import java.time.LocalTime;

/**
 * Point of interest as a depot point
 */
public class DepotAddress extends PointOfInterest {


    private LocalTime  departureTime;

    /**
     * Default constructor of DepotAddress
     */
    public DepotAddress() {
        super();
        departureTime = LocalTime.parse("00:00:00");
    }

    /**
     * Constructor of DepotAddress
     * @param i the intersection of this depot address
     * @param departureTime the time to depart of the depot point
     */
    public DepotAddress(Intersection i, String departureTime) {
        super(i,0);
        String[] fractureddepartureTime= departureTime.split(":");
        for(int j=0;j<3;j++){
            if (fractureddepartureTime[j].length()<2){
                fractureddepartureTime[j]="0"+fractureddepartureTime[j];
            }
        }
        this.departureTime = LocalTime.parse(fractureddepartureTime[0]+":"+fractureddepartureTime[1]+":"+fractureddepartureTime[2]);
        this.duration=0;
    }

    /**
     * Compares this depot address with another object and check if they are equal
     * @param obj the object to be compared with
     */
    @Override
    public boolean equals(Object obj) {
        if(!super.equals(obj)) {
            return false;
        }
        if(!this.departureTime.equals(((DepotAddress) obj).departureTime)){
            return false;
        }
        return true;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }
}