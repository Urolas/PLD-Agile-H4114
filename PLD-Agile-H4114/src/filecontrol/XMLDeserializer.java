/**
 * XMLDeserializer
 * @author 4IF-4114
 */

package filecontrol;

import java.io.File;
import java.io.IOException;
import java.lang.String;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.CityMap;
import model.Intersection;

/**
 * Parse the selected .XML file (map or requests)
 * */
public class XMLDeserializer {

    /**
     * Constructor of XMLDeserializer
     */
    public XMLDeserializer() {
    }

    /**
     * Open an XML file (map) and update the empty citymap
     * @param cityMap an emptyCityMap
     * @throws ParserConfigurationException when error in parsing
     * @throws SAXException when a specific information in the .xml can not be found
     * @throws IOException when the file does not exist
     * @throws XMLException when the format is wrong
     */
    public static void loadCityMap(CityMap cityMap) throws ParserConfigurationException, SAXException, IOException, XMLException {
        File xml = XMLFileOpener.getInstance().open(true);
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = docBuilder.parse(xml);
        Element root = document.getDocumentElement();
        if (root.getNodeName().equals("map")) {
            buildCityMapFromDOMXML(root, cityMap);
        } else {
            throw new XMLException("Wrong format");
        }
    }

    /**
     * Open an XML file (requests) and update the cityMap with the newly created Distribution
     * @param cityMap an emptyCityMap
     * @throws ParserConfigurationException when error in parsing
     * @throws SAXException when a specific information in the .xml can not be found
     * @throws IOException when the file does not exist
     * @throws XMLException when the format is wrong
     */
    public static void loadDistribution(CityMap cityMap) throws ParserConfigurationException, SAXException, IOException, XMLException {
        File xml = XMLFileOpener.getInstance().open(true);
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = docBuilder.parse(xml);
        Element root = document.getDocumentElement();
        if (root.getNodeName().equals("planningRequest")) {
            buildDistributionFromDOMXML(root, cityMap);
        } else {
            throw new XMLException("Wrong format");
        }
    }

    /**
     * Parse the citymap with the data from the xml file
     * @param rootDOMNode the root of the opened xml file
     * @param cityMap the current cityMap
     * @throws NumberFormatException when the format is wrong
     */
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

    /**
     * Add the Distribution list to the citymap
     * @param rootDOMNode the root of the opened xml file
     * @param cityMap the current cityMap
     * @throws NumberFormatException when the format is wrong
     * @throws XMLException when the xml file does not have the right format
     */
    private static void buildDistributionFromDOMXML(Element rootDOMNode, CityMap cityMap) throws NumberFormatException,XMLException {

        cityMap.distribution.reset();
        cityMap.tour.resetTour();
        Element depot = (Element) rootDOMNode.getElementsByTagName("depot").item(0);
        String address =depot.getAttribute("address");
        Intersection intersec =cityMap.getIntersections().get(address);
        if ( intersec==null){
            throw new XMLException("Wrong File used : depot point is not valid");
        }
        cityMap.distribution.addDepot(intersec,depot.getAttribute("departureTime"));
        NodeList requestList = rootDOMNode.getElementsByTagName("request");
        for (int i = 1; i < 1+requestList.getLength()*2; i+=2) {
            Element elt = (Element) requestList.item((i-1)/2);

            String pickupAddress =elt.getAttribute("pickupAddress");
            String deliveryAddress =elt.getAttribute("deliveryAddress");
            Intersection intersecPickup =cityMap.getIntersections().get(pickupAddress);
            Intersection intersecDelivery =cityMap.getIntersections().get(deliveryAddress);
            if ( intersecPickup==null || intersecDelivery==null){
                throw new XMLException("Wrong File used : request is not valid");
            }

            Integer pickupDuration = Integer.parseInt(elt.getAttribute("pickupDuration"));
            Integer deliveryDuration = Integer.parseInt(elt.getAttribute("deliveryDuration"));

            cityMap.distribution.addRequest(pickupDuration,deliveryDuration,intersecPickup,intersecDelivery,i);
        }


    }


}