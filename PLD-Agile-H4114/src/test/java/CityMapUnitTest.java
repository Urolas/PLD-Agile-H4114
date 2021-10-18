
import org.junit.*;
import model.CityMap;

import static org.junit.Assert.*;

public class CityMapUnitTest {
    CityMap cm;

    @Before
    public void before(){
        cm = new CityMap();
    }

    @Test
    public void testTrue(){
        assert(true);
    }

    @Test
    public void defaultConstructeurTest(){
        assertNotNull(cm);
    }




}
