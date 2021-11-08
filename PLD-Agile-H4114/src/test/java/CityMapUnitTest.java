
import model.*;
import org.junit.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import filecontrol.XMLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
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
            loadCityMap(cm,"src/test/testRessources/RouffiacCityMap.xml");
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
    public void structNotEmptyTest() {
        assertNotEquals(0,cm.getIntersections().size());
        assertNotEquals(0,cm.getAdjacencyList().size());
        assertNotEquals(0, cm.getRoads().size());
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
        assertEquals(String.format("%.3f",46.110),String.format("%.3f",cm.getNordPoint()));
        assertEquals(String.format("%.3f",109.523),String.format("%.3f",cm.getWestPoint()));
    }

    @Test
    public void containsRoadTest(){
        assertEquals(34,cm.getRoads().size());
        for(AbstractMap.SimpleEntry<String,String> keyValue : cm.getRoads().keySet()){
            assertEquals(roadsExpectedSet.get(keyValue),cm.getRoads().get(keyValue));
        }
        assertEquals(roadsExpectedSet,cm.getRoads());
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

    public static void loadCityMap(CityMap cityMap, String path) throws ParserConfigurationException, SAXException, IOException, XMLException {

        File xml = new File(path);
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
            intersectionsExpected.put(id,new Intersection(id,latitude,longitude));
            adjacencyListExpected.put(id, new LinkedList<>());
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
            Road r = new Road(name,length);
            r.addRoads(intersectionsExpected.get(id1),intersectionsExpected.get(id2));
            roadsExpectedSet.put(new AbstractMap.SimpleEntry<>(id1,id2),r);
            adjacencyListExpected.get(id1).add(new AbstractMap.SimpleEntry<>(id2,length));
            cityMap.completeAdjacencyList(id1, id2,length);
        }
    }


}
