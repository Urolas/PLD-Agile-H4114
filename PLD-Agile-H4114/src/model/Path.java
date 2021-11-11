/**
 * Path
 * @author 4IF-4114
 */
package model;

import java.util.List;
import java.util.Set;

/**
 * An ordered list of road connecting two points of interest
 */
public class Path {

    /**
     * Default constructor of Path
     */
    public Path() {
    }

    private List<Road> roads;
    private Double length;

    /**
     * Constructor of Path
     * @param roadsEndToEnd the list of road
     * @param key the length of the path
     */
    public Path(List<Road> roadsEndToEnd, Double key) {
        this.roads= roadsEndToEnd;
        this.length=key;
    }

    public List<Road> getRoads() {
        return roads;
    }

    public Double getLength() {
        return length;
    }
}