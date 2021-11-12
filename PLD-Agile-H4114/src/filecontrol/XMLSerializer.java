/**
 * XMLSerializer
 * @author 4IF-4114
 */

package filecontrol;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import model.*;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Write the roadmap into the created .xml file
 * */
public class XMLSerializer {

    private static Element pointRoot;
    private static Element roadRoot;
    private static Document document;
    private static XMLSerializer instance = null;
    private XMLSerializer(){}

    public static XMLSerializer getInstance(){
        if (instance == null)
            instance = new XMLSerializer();
        return instance;
    }

    /**
     * Add an attribute to an element
     * @param root the root of the Element
     * @param name the name of the attribute
     * @param value the value of the attribute
     */
    private static void createAttribute(Element root, String name, String value){
        Attr attribut = document.createAttribute(name);
        root.setAttributeNode(attribut);
        attribut.setValue(value);
    }

    /**
     * Add the roadmap to the xml file
     * @param cityMap the citymap with the tour to serialize
     * @return the roadmap Element
     */
    private static Element createRoadMap(CityMap cityMap) {

        //Set the document as the root of the element roadmap
        Element root = document.createElement("roadmap");

        //Get the informations from the Citymap
        int arrivalTime = cityMap.getDistribution().getDepot().getDepartureTime().toSecondOfDay();
        List<PointOfInterest> pointList = cityMap.getTour().getPointOfInterests();
        List<Path> pathList = cityMap.getTour().getPaths();

        //Create a roadmap element
        display(pointList.get(0), arrivalTime, true);
        root.appendChild(pointRoot);

        //Loop checking each point of interest from the list
        for (int poiNum = 1; poiNum < pointList.size(); poiNum++) {

            //Get the information about the point of interest and its path from the id
            PointOfInterest poi = pointList.get(poiNum);
            Path path = (Path) (pathList.get(poiNum-1));
            arrivalTime += (int)(path.getLength() / 15000. * 3600.); // Add the path's duration on the arrivalTime

            //Create the element point of interest
            display(poi, arrivalTime, false);
            root.appendChild(pointRoot);

            arrivalTime += poi.getDuration(); //Add the point's duration on the arrivalTime

            double length = 0;
            String name;
            int nbIntersection = 0;
            int durationRoad = 0;

            //Loop checking each road of the path
            for (int j = 0; j < path.getRoads().size(); ++j) {

                Road road = path.getRoads().get(j);
                length += road.getLength();
                name = road.getName();
                durationRoad += (int) (road.getLength() / 15000. * 3600.);

                if (j+1 < path.getRoads().size() && name.equals(path.getRoads().get(j+1).getName())) {
                    continue;
                }

                Road mergedRoad = new Road(name,length);

                //Create the element road inside the point of interest
                display(mergedRoad,durationRoad);
                pointRoot.appendChild(roadRoot);

                length = 0;
                durationRoad = 0;

            }
        }
        return root;
    }

    /**
     * Add a point of interest to the roadmap
     * @param p the point of interest we want to add
     * @param arrivalTime the arrivalTime to this point
     * @param start True if it's the starting depot point, else, False
     */
    public static void display(PointOfInterest p, int arrivalTime, boolean start) {

        //Add a point of interest's information as attributes

        pointRoot = document.createElement("pointOfInterest");
        String type="";
        if (p instanceof DeliveryAddress) {
            type="delivery";
            createAttribute(pointRoot,"id", Integer.toString((p.getIdPointOfInterest()-2)/2+1));
        }else if(p instanceof PickupAddress){
            type="pickup";
            createAttribute(pointRoot,"id", Integer.toString((p.getIdPointOfInterest()-1)/2+1));
        }else{
            type="depot";
            createAttribute(pointRoot,"id", Integer.toString(p.getIdPointOfInterest()));
        }
        createAttribute(pointRoot,"type",type);
        createAttribute(pointRoot,"latitude",Double.toString(p.getIntersection().getLatitude()));
        createAttribute(pointRoot,"longitude",Double.toString(p.getIntersection().getLongitude()));

        //Convert the time (seconds) into hours:minutes:seconds
        int hours = arrivalTime / 3600;
        int minutes = (arrivalTime % 3600) / 60;
        int seconds = arrivalTime % 60;
        if(start) {
            createAttribute(pointRoot, "timedeparture", String.format("%02d:%02d:%02d", hours, minutes, seconds));
        }else{
            int departureTime = arrivalTime+p.getDuration();
            createAttribute(pointRoot, "timedeparture", String.format("%02d:%02d:%02d", departureTime / 3600, (departureTime % 3600)/60, departureTime % 60));
            createAttribute(pointRoot, "timearrival", String.format("%02d:%02d:%02d", hours, minutes, seconds));
        }

    }

    /**
     * Add a road to the point of interest to show the path from the last point to this point
     * @param r the road to add
     * @param durationRoad the duration on this road
     */
    public static void display(Road r, int durationRoad){

        //Add a road's information as attributes
        roadRoot = document.createElement("road");
        createAttribute(roadRoot,"name",r.getName());
        createAttribute(roadRoot,"length",Double.toString(r.getLength()));
        int minutes = ((durationRoad %3600)/ 60);
        int seconds = (durationRoad % 60);
        createAttribute(roadRoot,"duration",String.format("%02d:%02d:%02d", 0 ,minutes, seconds));
    }

    /**
     * Open an XML file and write an XML description of the roadmap in it
     * @param cityMap the citymap with the tour to serialize
     * @param xml the .xml File to write on
     * @throws ParserConfigurationException when the parsing is null
     * @throws TransformerFactoryConfigurationError when the data can not written into the Document
     * @throws TransformerException when the path is wrong
     * @throws XMLException when the XML format is wrong
     */
    public static void save(CityMap cityMap, File xml) throws ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException, XMLException{

        //Save the information to the empty .xml file with a XML format
        StreamResult result = new StreamResult(xml);
        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        document.appendChild(createRoadMap(cityMap));
        DOMSource source = new DOMSource(document);
        Transformer xformer = TransformerFactory.newInstance().newTransformer();
        xformer.setOutputProperty(OutputKeys.INDENT, "yes");
        xformer.transform(source, result);
    }

}
