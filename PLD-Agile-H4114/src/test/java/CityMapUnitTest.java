
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
    }

    @Test
    public void emptyAfterResetTest(){
        cm.reset();
        assertEquals(cm.getIntersections().size(),0);
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
