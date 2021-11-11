
import model.*;
import org.junit.*;

import java.awt.*;
import java.util.List;
import java.util.*;

import static org.junit.Assert.*;

public class DistributionUnitTest {
    public static CityMap cm;
    public static Distribution distribution;
    public static Set<Request> requestsSetExpected;

    @BeforeClass
    public static void before() {
        cm = new CityMap();
        distribution = new Distribution();
        requestsSetExpected = new HashSet<>();

    }

    @Before
    public void initialisation() {
        try {
            XMLDeserializerForTests.loadCityMap(cm, "src/test/testRessources/RouffiacCityMap.xml");
            XMLDeserializerForTests.loadDistribution(cm, "src/test/testRessources/DeliveryBasic.xml");
            distribution = cm.getDistribution();
            Request r1 = new Request();
            Request r2 = new Request();
        } catch (Exception e) {
            System.err.println("Erreur lors de la lecture du fichier");
        }
    }

    @After
    public void reset() {
        distribution.reset();
    }

    @Test
    public void constructorTest() {
        assertNotNull(distribution.getRequests());
        assertNotNull(distribution.getDepot());
        assertNotNull(distribution.getRequests());
    }

    @Test
    public void afterResetTest() {
        distribution.reset();
        Distribution d = new Distribution();
        assertEquals(d.getDepot(), distribution.getDepot());
        assertEquals(d.getColorList(), distribution.getColorList());
        assertEquals(d.getRequests(), distribution.getRequests());
    }

    @Test
    public void addDepotTest() {
        distribution.reset();
        distribution.addDepot(new Intersection("030504", 12.58, 59.65), "8:3:24");
        DepotAddress d = new DepotAddress(new Intersection("030504", 12.58, 59.65), "08:03:24");
        assertEquals(d, distribution.getDepot());
    }

    @Test
    public void addRequestTest() {
        distribution.reset();
        Intersection i1 = new Intersection("123", 12.18, 25.9);
        Intersection i2 = new Intersection("321", 18.12, 9.25);
        distribution.addRequest(120, 60, i1, i2, 1);
        Request r = new Request(new PickupAddress(i1, 120, 1), new DeliveryAddress(i2, 60, 2), Color.decode("#1FBED6"));
        for (Request r2 : distribution.getRequests()) {
            assertEquals(r, r2);
        }
    }

    @Test
    public void pointsAfterXMLTest() {
        List<PointOfInterest> points = distribution.GetAllPoints();
        Set<PointOfInterest> pointsSet = new HashSet<>(points);

        DepotAddress d = new DepotAddress(new Intersection("811503", 45.647, 110.623), "8:15:0");
        PickupAddress p1 = new PickupAddress(new Intersection("811500", 46.110, 110.213), 120, 1);
        DeliveryAddress d1 = new DeliveryAddress(new Intersection("8115010", 45.503, 110.453), 60, 2);
        PickupAddress p2 = new PickupAddress(new Intersection("8115012", 45.462, 110.199), 240, 3);
        DeliveryAddress d2 = new DeliveryAddress(new Intersection("811505", 45.342, 112.012), 60, 4);


        assertEquals(d, points.get(0));

        for (int i = 1; i < 5; i++) {
            switch (points.get(i).getIdPointOfInterest()) {
                case 1:
                    assertEquals(p1, points.get(i));
                    break;
                case 3:
                    assertEquals(p2, points.get(i));
                    break;
                case 2:
                    assertEquals(d1, points.get(i));
                    break;
                case 4:
                    assertEquals(d2, points.get(i));
                    break;
                default:
                    fail();
            }
        }


    }

    @Test
    public void contraintsAfterXMLTest() {
        HashMap<Integer, Integer> contraintDict = distribution.GetConstraints();

        assertEquals(2, contraintDict.size());
        assertEquals((Integer) 2, contraintDict.get(1));
        assertEquals((Integer) 4, contraintDict.get(3));

    }

    @Test
    public void requestsAfterXMLTest(){
        Set<Request> requests = distribution.getRequests();

        PickupAddress p1 = new PickupAddress(new Intersection("811500", 46.110, 110.213), 120, 1);
        DeliveryAddress d1 = new DeliveryAddress(new Intersection("8115010", 45.503, 110.453), 60, 2);
        PickupAddress p2 = new PickupAddress(new Intersection("8115012", 45.462, 110.199), 240, 3);
        DeliveryAddress d2 = new DeliveryAddress(new Intersection("811505", 45.342, 112.012), 60, 4);
        Request r1 = new Request(p1,d1,Color.decode("#1FBED6"));
        Request r2 = new Request(p2,d2,Color.decode("#97C30A"));

        assertEquals(2,requests.size());
        for(Request r : requests){
            if(r.getPickup().getIdPointOfInterest()==1){
                assertEquals(r1,r);

            }else{
                assertEquals(r2,r);
            }
        }

    }

    @Test
    public void getDeliveryFromPickupTest(){
        for(Request r : distribution.getRequests()){
            PickupAddress p = r.getPickup();
            assertEquals(r.getDelivery(),distribution.getDelivery(p));
        }
    }

    @Test
    public void getDeliveryFromNonExistingPickupTest(){
        Intersection i = cm.getIntersections().get("8115011");
        PickupAddress p = new PickupAddress(i,120,6);

        assertNull(distribution.getDelivery(p));
    }

    @Test
    public void getPickupFromDeliveryTest(){
        for(Request r : distribution.getRequests()){
            DeliveryAddress d = r.getDelivery();
            assertEquals(r.getPickup(),distribution.getPickup(d));
        }
    }

    @Test
    public void getPickupFromNonExistingDeliveryTest(){
        Intersection i = cm.getIntersections().get("8115011");
        DeliveryAddress d = new DeliveryAddress(i,120,6);

        assertNull(distribution.getPickup(d));
    }
}
