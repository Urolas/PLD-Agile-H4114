package model;

import java.util.List;
import java.util.Set;

/**
 * @author 4IF-4114
 */
public class Path {

    /**
     * Default constructor
     */
    public Path() {
    }

    private List<Road> roads;
    private Double length;

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