package xml;

import java.util.*;
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
import model.Road;
import model.Distribution;
import model.Request;
import model.DepotAddress;

/**
 * @author 4IF-4114
 */
public class XMLDeserializer {

    /**
     * Default constructor
     */
    public XMLDeserializer() {
    }

    /**
     * Open an XML file and create plan from this file
     *
     * @param cityMap
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws XMLException
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
     * @param cityMap
     * @return
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
     * @param rootDOMNode
     * @param cityMap
     * @return
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
            cityMap.addIntersection(createIntersection(id, latitude, longitude));
        }
        cityMap.setHeight(maxLongitude-minLongitude);
        cityMap.setWidth(maxLatitude-minLatitude);
        NodeList roadList = rootDOMNode.getElementsByTagName("segment");
        for (int i = 0; i < roadList.getLength(); i++) {
            Element elt = (Element) roadList.item(i);
            String id1 = elt.getAttribute("origin");
            String id2 = elt.getAttribute("destination");
            cityMap.addRoad(createRoad(elt), id1, id2);
        }
    }

    /**
     * Citymap mis en argument pour pouvoir recuperer les intersections, qu'on va ensuite ratacher aux points d'interet
     * */
    private static void buildDistributionFromDOMXML(Element rootDOMNode, CityMap cityMap) throws NumberFormatException,XMLException {

        cityMap.distribution.reset();
        Element depot = (Element) rootDOMNode.getElementsByTagName("depot").item(0);
        String address =depot.getAttribute("address");
        Intersection intersec =cityMap.getIntersections().get(address);
        if ( intersec==null){
            throw new XMLException("Wrong File used : depot point is not valid");
        }
        cityMap.distribution.addDepot(intersec,depot.getAttribute("departureTime"));
        NodeList requestList = rootDOMNode.getElementsByTagName("request");
        for (int i = 0; i < requestList.getLength(); i++) {
            Element elt = (Element) requestList.item(i);

            String pickupAddress =elt.getAttribute("pickupAddress");
            String deliveryAddress =elt.getAttribute("deliveryAddress");
            Intersection intersecPickup =cityMap.getIntersections().get(pickupAddress);
            Intersection intersecDelivery =cityMap.getIntersections().get(deliveryAddress);
            if ( intersecPickup==null || intersecDelivery==null){
                throw new XMLException("Wrong File used : request is not valid");
            }

            Integer pickupDuration = Integer.parseInt(elt.getAttribute("pickupDuration"));
            Integer deliveryDuration = Integer.parseInt(elt.getAttribute("deliveryDuration"));

            cityMap.distribution.addRequest(pickupDuration,deliveryDuration,intersecPickup,intersecDelivery);
        }


    }




    /**
     * @param id,latitude,longitude
     * @return
     */
    private static Intersection createIntersection(String id, Double latitude,Double longitude) {
        return new Intersection(id, latitude, longitude);
    }

    /**
     * @param elt
     * @return
     */
    private static Road createRoad(Element elt) {

        String name = elt.getAttribute("name");
        Double length = Double.parseDouble(elt.getAttribute("length"));

        return new Road(name, length);
    }



}