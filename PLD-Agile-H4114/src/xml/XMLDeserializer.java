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
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws XMLException
     * @param cityMap 
     * @return
     */
    public static void loadCityMap(CityMap cityMap) throws ParserConfigurationException, SAXException, IOException, XMLException {
        File xml = XMLFileOpener.getInstance().open(true);
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = docBuilder.parse(xml);
        Element root = document.getDocumentElement();
        if (root.getNodeName().equals("map")) { //unsure
            buildCityMapFromDOMXML(root, cityMap);
        }
        else {
            throw new XMLException("Wrong format");
        }
    }

    /**
     * @param distribution 
     * @return
     */
    public static void loadDistribution(Distribution distribution) {
        // TODO implement here
    }

    /**
     * @param rootDOMNode 
     * @param cityMap 
     * @return
     */
    private static void buildCityMapFromDOMXML(Element rootDOMNode, CityMap cityMap) throws XMLException, NumberFormatException {

        cityMap.reset();

        NodeList intersectionList = rootDOMNode.getElementsByTagName("intersection");
        for (int i = 0; i < intersectionList.getLength(); i++) {
            cityMap.addIntersection(createIntersection((Element) intersectionList.item(i)));
        }

        NodeList roadList = rootDOMNode.getElementsByTagName("segment");
        for (int i = 0; i < roadList.getLength(); i++) {
            Element elt= (Element) roadList.item(i);
            String id1 = elt.getAttribute("origin");
            String id2 = elt.getAttribute("destination");
            cityMap.addRoad(createRoad(elt),id1,id2);
        }
    }



    /**
     * @param rootDOMNode 
     * @param distribution 
     * @return
     */
    public static final void buildDistributionFromDOMXML(Element rootDOMNode, Distribution distribution)  {
        // TODO implement here
    }

    /**
     * @param elt 
     * @return
     */
    private static Intersection createIntersection(Element elt) {
        String id = elt.getAttribute("id");
        Double latitude = Double.parseDouble(elt.getAttribute("latitude"));
        Double longitude = Double.parseDouble(elt.getAttribute("longitude"));

        return new Intersection(id,latitude,longitude);
    }

    /**
     * @param elt 
     * @return
     */
    private static Road createRoad(Element elt) {

        String name = elt.getAttribute("name");
        Double length = Double.parseDouble(elt.getAttribute("length"));

        return new Road(name,length);
    }

    /**
     * @param elt 
     * @return
     */
    public static Request createRequest(Element elt) {
        // TODO implement here
        return null;
    }

    /**
     * @param elt 
     * @return
     */
    public static final DepotAddress createDepot(Element elt) {
        // TODO implement here
        return null;
    }

}