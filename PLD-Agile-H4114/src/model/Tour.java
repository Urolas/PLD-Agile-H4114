package model;

import java.util.Set;

/**
 * @author 4IF-4114
 */
public class Tour {
    private Set<Path> paths;

    public Set<PointOfInterest> pointOfInterests;

    /**
     * Default constructor
     */
    public Tour() {
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() != Tour.class) {
            return false;
        }
        if (!paths.equals(((Tour) o).paths)) {
            return false;
        }
        if (!pointOfInterests.equals(((Tour) o).pointOfInterests)) {
            return false;
        }
        return true;


    }
}