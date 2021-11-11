/**
 * Path
 *
 * @author 4IF-4114
 */
package model;

import java.util.List;


/**
 * An ordered list of road connecting two points of interest
 */
public class Path {


    private final List<Road> ROADS;
    private final Double LENGTH;

    /**
     * Constructor of Path
     * @param roadsEndToEnd the list of road
     * @param key the length of the path
     */
    public Path(List<Road> roadsEndToEnd, Double key) {
        this.ROADS = roadsEndToEnd;
        this.LENGTH = key;
    }

    public List<Road> getRoads() {
        return ROADS;
    }

    public Double getLength() {
        return LENGTH;
    }
}