
import model.CityMap;
import model.Distribution;
import model.Intersection;
import model.Request;
import org.junit.*;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DistributionUnitTest {
    public static CityMap cm ;
    public static Distribution distribution;
    public static Set<Request> requestsSetExpected;

    @BeforeClass
    public static void before(){
        cm = new CityMap();
        distribution = new Distribution();
        requestsSetExpected = new HashSet<>();

    }

    @Before
    public void initialisation(){
        try{
            XMLDeserializerForTests.loadCityMap(cm,"src/test/testRessources/RouffiacCityMap.xml");
            XMLDeserializerForTests.loadDistribution(cm,"src/test/testRessources/DeliveryBasic.xml");
            distribution = cm.getDistribution();
            Request r1 = new Request();
            Request r2 = new Request();
        }catch (Exception e){
            System.err.println("Erreur lors de la lecture du fichier");
        }
    }

    @After
    public void reset(){distribution.reset();}

    @Test
    public void constructorTest(){
        assertNotNull(distribution.getRequests());
        assertNotNull(distribution.getDepot());
        assertNotNull(distribution.getRequests());
        ArrayList<String> colors = new ArrayList<>();
        colors.add("#1FBED6");
        colors.add("#97C30A");
        colors.add("#FF717E");
        colors.add("#FFDE00");
        colors.add("#006666");
        colors.add("#FFFFFF");
        assertEquals(colors,distribution.getColorList());
    }

    @Test
    public void afterResetTest(){
        distribution.reset();
        Distribution d = new Distribution();
        assertEquals(d.getDepot(),distribution.getDepot());
        assertEquals(d.getColorList(),distribution.getColorList());
        assertEquals(d.getRequests(),distribution.getRequests());
    }

    @Test
    public void addDepotTest(){
        distribution.addDepot(new Intersection("030504",12.58,59.65),"8:3:24");
//        assertEquals();
    }
}
