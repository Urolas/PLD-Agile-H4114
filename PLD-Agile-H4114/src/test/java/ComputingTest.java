import model.CityMap;
import model.Path;
import model.PointOfInterest;
import model.Tour;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ComputingTest {
    public static CityMap cm;

    @BeforeClass
    public static void before() {
        cm = new CityMap();
        try {
            XMLDeserializerForTests.loadCityMap(cm, "src/test/testRessources/RouffiacCityMap.xml");
            XMLDeserializerForTests.loadDistribution(cm, "src/test/testRessources/DeliveryBasic.xml");
        } catch (Exception e) {
            System.err.println("Erreur lors de la lecture du fichier");
        }
    }

    @Test
    public void computePathTest() {
        List<PointOfInterest> lp = cm.getDistribution().GetAllPoints();
        PointOfInterest p1 = new PointOfInterest();
        PointOfInterest p2 = new PointOfInterest();

        for (PointOfInterest p : lp) {
            if (p.getIdPointOfInterest() == 0) {
                p1 = p;
            } else if (p.getIdPointOfInterest() == 4) {
                p2 = p;
            }
        }

        AbstractMap.SimpleEntry<Double, List<String>> path = cm.computePath(p1, p2);

        assertEquals("15,080", String.format("%.3f", path.getKey()));

        List<String> intersections = path.getValue();

        assertEquals("811503", intersections.get(0));
        assertEquals("8115013", intersections.get(1));
        assertEquals("811509", intersections.get(2));
        assertEquals("811504", intersections.get(3));
        assertEquals("811505", intersections.get(4));


    }

    @Test
    public void lengthOfPathForTourTest(){
        List<PointOfInterest> lp = cm.getDistribution().GetAllPoints();
        PointOfInterest[] pointsSorted =new PointOfInterest[5];

        for(PointOfInterest p : lp){
            pointsSorted[p.getIdPointOfInterest()]=p;
        }
        assertEquals("6,558",String.format("%.3f", cm.computePath(pointsSorted[0], pointsSorted[1]).getKey()));
        assertEquals("5,533",String.format("%.3f", cm.computePath(pointsSorted[1], pointsSorted[2]).getKey()));
        assertEquals("0,887",String.format("%.3f", cm.computePath(pointsSorted[2], pointsSorted[3]).getKey()));
        assertEquals("14,758",String.format("%.3f", cm.computePath(pointsSorted[3], pointsSorted[4]).getKey()));
        assertEquals("15,080",String.format("%.3f", cm.computePath(pointsSorted[4], pointsSorted[0]).getKey()));
    }

    @Test
    public void computeTourTest(){
        cm.computeTour();
        Tour t = cm.getTour();
        List<PointOfInterest> lp = t.getPointOfInterests();

        assertEquals((Integer) 0,lp.get(0).getIdPointOfInterest());
        assertEquals((Integer) 1,lp.get(1).getIdPointOfInterest());
        assertEquals((Integer) 2,lp.get(2).getIdPointOfInterest());
        assertEquals((Integer) 3,lp.get(3).getIdPointOfInterest());
        assertEquals((Integer) 4,lp.get(4).getIdPointOfInterest());
        assertEquals((Integer) 0,lp.get(5).getIdPointOfInterest());

        assertEquals("42,816",String.format("%.3f",t.getTotalLength()));

        List<Path> paths = t.getPaths();

        assertEquals("6,558",String.format("%.3f", paths.get(0).getLength()));
        assertEquals("5,533",String.format("%.3f", paths.get(1).getLength()));
        assertEquals("0,887",String.format("%.3f", paths.get(2).getLength()));
        assertEquals("14,758",String.format("%.3f", paths.get(3).getLength()));
        assertEquals("15,080",String.format("%.3f", paths.get(4).getLength()));

    }


}
