import model.CityMap;
import model.Intersection;
import model.PointOfInterest;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.*;
import java.util.List;

public class TourModificationTest {
    public static CityMap cm;


    @BeforeClass
    public static void beforeClass() {
        cm = new CityMap();
    }

    @Before
    public void setUp() throws Exception {
        try{
            XMLDeserializerForTests.loadCityMap(cm,"src/test/testRessources/RouffiacCityMap.xml");
            XMLDeserializerForTests.loadDistribution(cm,"src/test/testRessources/DeliveryBasic.xml");
        }catch (Exception e){
            System.err.println("Erreur lors de la lecture du fichier");
        }
        cm.computeTour();
    }

    @Test
    public void addRequest(){
        List<PointOfInterest> listPOI = cm.getTour().getPointOfInterests();

        Intersection i1 = cm.getIntersections().get("811508");
        PointOfInterest p1 = new PointOfInterest(i1,5); /*TODO : changer avec le compteur interne*/
        Intersection i2 = cm.getIntersections().get("811509");
        PointOfInterest p2 = new PointOfInterest(i2,6);

    }
}
