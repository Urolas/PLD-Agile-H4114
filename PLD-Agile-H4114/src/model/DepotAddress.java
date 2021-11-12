/**
 * DepotAddress
 *
 * @author 4IF-4114
 */
package model;

import java.time.LocalTime;

/**
 * Point of interest as a depot point
 */
public class DepotAddress extends PointOfInterest {


    private final LocalTime departureTime;

    /**
     * Default constructor of DepotAddress
     */
    public DepotAddress() {
        super();
        departureTime = LocalTime.parse("00:00:00");
    }

    /**
     * Constructor of DepotAddress
     *
     * @param intersection  the intersection of this depot address
     * @param departureTime the time to depart of the depot point
     */
    public DepotAddress(Intersection intersection, String departureTime) {
        super(intersection, 0);
        String[] fracturedDepartureTime = departureTime.split(":");
        for (int j = 0; j < 3; j++) {
            if (fracturedDepartureTime[j].length() < 2) {
                fracturedDepartureTime[j] = "0" + fracturedDepartureTime[j];
            }
        }
        this.departureTime = LocalTime.parse(fracturedDepartureTime[0] + ":" + fracturedDepartureTime[1] + ":" + fracturedDepartureTime[2]);
        this.duration = 0;
    }

    /**
     * Compares this depot address with another object and check if they are equal
     *
     * @param obj the object to be compared with
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        return this.departureTime.equals(((DepotAddress) obj).departureTime);
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }
}