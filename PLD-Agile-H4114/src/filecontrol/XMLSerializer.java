package filecontrol;

import java.io.File;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import model.PointOfInterest;
import org.xml.sax.SAXException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import model.CityMap;

public class XMLSerializer {

    private Element elementRoot;
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
        return root;
    }

    private void createAttribute(Element root, String name, String value){
        Attr attribut = document.createAttribute(name);
        root.setAttributeNode(attribut);
        attribut.setValue(value);
    }

}
