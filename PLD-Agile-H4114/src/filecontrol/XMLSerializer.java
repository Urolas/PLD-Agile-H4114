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
     * @param plan the plan to serialise
     * @throws ParserConfigurationException
     * @throws TransformerFactoryConfigurationError
     * @throws TransformerException
     * @throws ExceptionXML
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

    private static void createAttribute(Element root, String name, String value){
        Attr attribut = document.createAttribute(name);
        root.setAttributeNode(attribut);
        attribut.setValue(value);
    }

    public static void display(PointOfInterest p, int arrivalTime, boolean start) {
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

    public static void display(Road r, int durationRoad){
        roadRoot = document.createElement("road");
        createAttribute(roadRoot,"name",r.getName());
        createAttribute(roadRoot,"length",Double.toString(r.getLength()));
        int minutes = ((durationRoad %3600)/ 60);
        int seconds = (durationRoad % 60);
        createAttribute(roadRoot,"duration",String.format("%02d:%02d:%02d", 0 ,minutes, seconds));
    }


}
