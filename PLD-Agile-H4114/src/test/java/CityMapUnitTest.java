
import model.Distribution;
import model.Tour;
import org.junit.*;
import model.CityMap;
import model.Intersection;
import org.junit.experimental.categories.Category;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import xml.XMLException;
import xml.XMLFileOpener;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
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
        try{
            loadCityMap(cm);
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

    public static void loadCityMap(CityMap cityMap) throws ParserConfigurationException, SAXException, IOException, XMLException {

        File xml = new File("src/test/testRessources/RouffiacCityMap.xml");
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = docBuilder.parse(xml);
        Element root = document.getDocumentElement();
        if (root.getNodeName().equals("map")) {
            buildCityMapFromDOMXML(root, cityMap);
        } else {
            throw new XMLException("Wrong format");
        }
    }

    private static void buildCityMapFromDOMXML(Element rootDOMNode, CityMap cityMap) throws NumberFormatException {

        cityMap.reset();
        Double maxLatitude = null;
        Double minLatitude = null;
        Double maxLongitude = null;
        Double minLongitude = null;

        NodeList intersectionList = rootDOMNode.getElementsByTagName("intersection");
        for (int i = 0; i < intersectionList.getLength(); i++) {
            Element elt = (Element) intersectionList.item(i);
            String id = elt.getAttribute("id");
            Double latitude = Double.parseDouble(elt.getAttribute("latitude"));
            Double longitude = Double.parseDouble(elt.getAttribute("longitude"));
            if (maxLatitude == null || maxLatitude < latitude) {
                maxLatitude = latitude;
            }
            if (minLatitude == null || minLatitude > latitude) {
                minLatitude = latitude;
            }
            if (maxLongitude == null || maxLongitude < longitude) {
                maxLongitude = longitude;
            }
            if (minLongitude == null || minLongitude > longitude) {
                minLongitude = longitude;
            }
            cityMap.addIntersection(new Intersection(id, latitude, longitude));
            cityMap.initializeAdjacencyList(id);
        }
        cityMap.setHeight(maxLatitude-minLatitude);
        cityMap.setWidth(maxLongitude-minLongitude);
        cityMap.setNordPoint(maxLatitude);  // La latitude indique un positionnement Nord-Sud
        cityMap.setWestPoint(minLongitude); // La longitude indique un positionnement Ouest-Est
        NodeList roadList = rootDOMNode.getElementsByTagName("segment");
        for (int i = 0; i < roadList.getLength(); i++) {
            Element elt = (Element) roadList.item(i);
            String id1 = elt.getAttribute("origin");
            String id2 = elt.getAttribute("destination");
            String name = elt.getAttribute("name");
            Double length = Double.parseDouble(elt.getAttribute("length"));
            cityMap.addRoad(name,length, id1, id2);
            cityMap.completeAdjacencyList(id1, id2,length);
        }
    }


}
