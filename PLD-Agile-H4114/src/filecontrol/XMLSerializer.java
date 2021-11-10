/**
 * XMLSerializer
 * @author 4IF-4114
 */

package filecontrol;

import java.io.File;
import java.util.Iterator;
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
import org.xml.sax.SAXException;
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
     * Open an XML file and write an XML description of the roadmap in it
     * @param citymap the citymap with the tour to serialize
     * @param xml the .xml File to write on
     * @throws ParserConfigurationException when the parsing is null
     * @throws TransformerFactoryConfigurationError when the data can not written into the Document
     * @throws TransformerException when the path is wrong
     * @throws ExceptionXML when the XML format is wrong
     */
    public static void save(CityMap citymap, File xml) throws ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException, XMLException{
        StreamResult result = new StreamResult(xml);
        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        document.appendChild(createRoadMap(citymap));
        DOMSource source = new DOMSource(document);
        Transformer xformer = TransformerFactory.newInstance().newTransformer();
        xformer.setOutputProperty(OutputKeys.INDENT, "yes");
        xformer.transform(source, result);
    }

    /**
     * Add the roadmap to the xml file
     * @param citymap the citymap with the tour to serialize
     * @return the roadmap Element
     */
    private static Element createRoadMap(CityMap citymap) {
        Element root = document.createElement("roadmap");
        int arrivalTime = citymap.getDistribution().getDepot().getDepartureTime().toSecondOfDay();
        List<PointOfInterest> pointList = citymap.getTour().getPointOfInterests();
        List<Path> pathList = citymap.getTour().getPaths();

        display(pointList.get(0), arrivalTime, true);
        root.appendChild(pointRoot);


        for (int poiNum = 1; poiNum < pointList.size(); poiNum++) {

            PointOfInterest poi = pointList.get(poiNum);
            Path path = (Path) (pathList.get(poiNum-1));
            arrivalTime += (int)(path.getLength() / 15000. * 3600.);

            display(poi, arrivalTime, false);
            root.appendChild(pointRoot);

            arrivalTime += poi.getDuration();

            double length = 0;
            String name;
            int nbIntersection = 0;
            int durationRoad = 0;


            for (int j = 0; j < path.getRoads().size(); ++j) {

                Road road = path.getRoads().get(j);
                length += road.getLength();
                name = road.getName();
                durationRoad += (int) (road.getLength() / 15000. * 3600.);

                if (j+1 < path.getRoads().size() && name.equals(path.getRoads().get(j+1).getName())) {
                    continue;
                }

                Road mergedRoad = new Road(name,length);

                display(mergedRoad,durationRoad);
                pointRoot.appendChild(roadRoot);

                length = 0;
                durationRoad = 0;


            }
        }

        return root;
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
     * Add a point of interest to the roadmap
     * @param p the point of interest we want to add
     * @param arrivalTime the arrivalTime to this point
     * @param start True if it's the starting depot point, else, False
     */
    public static void display(PointOfInterest p, int arrivalTime, boolean start) {
        pointRoot = document.createElement("pointOfInterest");
        String type="";
        if (p instanceof DeliveryAddress) {
            type="delivery";
        }else if(p instanceof PickupAddress){
            type="pickup";
        }else{
            type="depot";
        }
        createAttribute(pointRoot,"type",type);
        createAttribute(pointRoot,"id", Integer.toString(p.getIdPointOfInterest()));
        createAttribute(pointRoot,"latitude",Double.toString(p.getIntersection().getLatitude()));
        createAttribute(pointRoot,"longitude",Double.toString(p.getIntersection().getLongitude()));

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
        roadRoot = document.createElement("road");
        createAttribute(roadRoot,"name",r.getName());
        createAttribute(roadRoot,"length",Double.toString(r.getLength()));
        int minutes = ((durationRoad %3600)/ 60);
        int seconds = (durationRoad % 60);
        createAttribute(roadRoot,"duration",String.format("%02d:%02d:%02d", 0 ,minutes, seconds));
    }


}
