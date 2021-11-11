
import model.*;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.*;

public class CityMapUnitTest {
    public static CityMap cm;
    public static HashMap<String,Intersection> intersectionsExpected;
    public static HashMap<AbstractMap.SimpleEntry<String,String>,Road> roadsExpectedSet;
    public static HashMap<String, List<AbstractMap.Entry<String,Double>>> adjacencyListExpected;


    @BeforeClass
    public static void before(){
        cm = new CityMap();
        intersectionsExpected = new HashMap<>();
        roadsExpectedSet = new HashMap<>();
        adjacencyListExpected = new HashMap<>();

    }

    @Before
    public void initialisation(){
        try{
            XMLDeserializerForTests.loadCityMap(cm,"src/test/testRessources/RouffiacCityMap.xml",intersectionsExpected,roadsExpectedSet,adjacencyListExpected);
        }catch (Exception e){
            System.err.println("Erreur lors de la lecture du fichier");
        }
    }

    @After
    public void reset(){
        cm.reset();
    }

    @Test
    public void structNotNullTest(){
        assertNotNull(cm);
        assertNotNull(cm.getIntersections());
        assertNotNull(cm.getROADS());
        assertNotNull(cm.getDistribution());
        assertNotNull(cm.getTour());
        assertNotNull(cm.getWidth());
        assertNotNull(cm.getHeight());
        assertNotNull(cm.getNorthPoint());
        assertNotNull(cm.getWestPoint());
        assertNotNull(cm.getAdjacencyList());
    }

    @Test
    public void emptyAfterResetTest(){
        cm.reset();
        CityMap cm2 = new CityMap();
        assertEquals(cm.getIntersections(),cm2.getIntersections());
        assertEquals(cm.getROADS(),cm2.getROADS());
        assertTrue(cm.getDistribution().equals(cm2.getDistribution()));
        assertEquals(cm.getTour(),cm2.getTour());
        assertEquals(cm.getWidth(),cm2.getWidth());
        assertEquals(cm.getHeight(),cm2.getHeight());
        assertEquals(cm.getNorthPoint(),cm2.getNorthPoint());
        assertEquals(cm.getWestPoint(),cm2.getWestPoint());
        assertEquals(cm.getAdjacencyList(),cm2.getAdjacencyList());
    }

    @Test
    public void structNotEmptyTest() {
        assertNotEquals(0,cm.getIntersections().size());
        assertNotEquals(0,cm.getAdjacencyList().size());
        assertNotEquals(0, cm.getROADS().size());
    }

    @Test
    public void containsKeyTest() {
        //Verification de l'exitence de la clé
        Set<String> keySet= new HashSet<>();
        assertEquals(14,cm.getIntersections().size());
        for (int i = 0;i<14;i++){
            keySet.add("81150" + i);
        }
        assertEquals(cm.getIntersections().keySet(),keySet);
    }

    @Test
    public void heightTest(){
        assertEquals(String.format("%.3f",1.183),String.format("%.3f",cm.getHeight()));
    }

    @Test
    public void widthTest(){
        assertEquals(String.format("%.3f",2.489),String.format("%.3f",cm.getWidth()));
    }

    @Test
    public void nordWestPointsTest(){
        assertEquals(String.format("%.3f",46.110),String.format("%.3f",cm.getNorthPoint()));
        assertEquals(String.format("%.3f",109.523),String.format("%.3f",cm.getWestPoint()));
    }

    @Test
    public void containsRoadTest(){
        assertEquals(34,cm.getROADS().size());
        for(AbstractMap.SimpleEntry<String,String> keyValue : cm.getROADS().keySet()){
            assertEquals(roadsExpectedSet.get(keyValue),cm.getROADS().get(keyValue));
        }
        assertEquals(roadsExpectedSet,cm.getROADS());
    }

    @Test
    public void containsAdjacencyListTest(){
        assertEquals(14,cm.getAdjacencyList().size());
        assertEquals(adjacencyListExpected,cm.getAdjacencyList());
    }

    @Test
    public void toStringFormatTest(){
        assertEquals("CityMap{roads="+roadsExpectedSet+"}",cm.toString());
    }

}
