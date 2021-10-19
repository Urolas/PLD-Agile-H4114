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
    //Methode d'egalité entre les Tours
    public boolean equals(Object o) {
        //Meme class
        if (o.getClass() != Tour.class) {
            return false;
        }
        //Meme path
        if (!paths.equals(((Tour) o).paths)) {
            return false;
        }
        //Meme pointOfInterests
        if (!pointOfInterests.equals(((Tour) o).pointOfInterests)) {
            return false;
        }
        return true;


    }
}