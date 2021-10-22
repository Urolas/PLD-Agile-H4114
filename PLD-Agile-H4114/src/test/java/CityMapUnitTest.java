
import model.Distribution;
import model.Tour;
import org.junit.*;
import model.CityMap;
import model.Intersection;
import org.junit.experimental.categories.Category;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;


public class CityMapUnitTest {
    public static CityMap cm;


    @BeforeClass
    public static void before(){
        cm = new CityMap();
    }

    @Before
    public void initialisation(){
        Intersection i = new Intersection("123456", 15.20355, 24.658999);
        cm.addIntersection(i);
    }

    @After
    public void reset(){
        cm.reset();
    }

    @Test
    public void structNotNullTest(){
        assertNotNull(cm);
        assertNotNull(cm.getIntersections());
        assertNotNull(cm.getRoads());
        assertNotNull(cm.getDistribution());
        assertNotNull(cm.getTour());
        assertNotNull(cm.getWidth());
        assertNotNull(cm.getHeight());
        assertNotNull(cm.getNordPoint());
        assertNotNull(cm.getWestPoint());
        assertNotNull(cm.getAdjacencyList());
    }

    @Test
    public void emptyAfterResetTest(){
        cm.reset();
        CityMap cm2 = new CityMap();
        assertEquals(cm.getIntersections(),cm2.getIntersections());
        assertEquals(cm.getRoads(),cm2.getRoads());
        assertTrue(cm.getDistribution().equals(cm2.getDistribution()));
        assertEquals(cm.getTour(),cm2.getTour());
        assertEquals(cm.getWidth(),cm2.getWidth());
        assertEquals(cm.getHeight(),cm2.getHeight());
        assertEquals(cm.getNordPoint(),cm2.getNordPoint());
        assertEquals(cm.getWestPoint(),cm2.getWestPoint());
        assertEquals(cm.getAdjacencyList(),cm2.getAdjacencyList());
    }

    @Test
    public void intersectionNotNullTest() {
        assertNotEquals(cm.getIntersections().size(),0);
    }

    @Test
    public void containsKeyTest() {
        //Verification de l'exitence de la clé
        assertTrue(cm.getIntersections().containsKey("123456"));
    }

    @Test
    public void keySetEqualityTest(){
        Set<String> setTest = new HashSet<>();
        setTest.add("123456");
        assertEquals(cm.getIntersections().keySet(), setTest);
    }




}
