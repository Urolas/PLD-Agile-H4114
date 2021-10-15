import org.junit.Assert;
import org.junit.Test;


public class MyFirstTest {
    @Test
    public void firstTest() {
        Assert.assertTrue(true);
    }

    @Test
    public void equalTest() {
        String s = main.returnString();
        Assert.assertEquals("ça marche",s);
    }

    @Test
    public void failedTestTest() {
        String s = main.returnString();
        Assert.assertEquals("ça marche!",s);
    }

    @Test
    public void notEqualTest(){
        String s = main.returnString();
        Assert.assertNotEquals("blabla",s);
    }
}