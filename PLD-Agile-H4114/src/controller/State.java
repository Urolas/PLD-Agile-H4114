package controller;
import org.xml.sax.SAXException;
import view.MapView;
import view.Window;
import xml.XMLException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * @author 4IF-4114
 */
public interface State {


    /**
     * Method called by the controller after a click on the button "Load a map"
     * @param c the controller
     * @param w the window
     */
    public default void loadMap(Controller c, Window w) throws XMLException, ParserConfigurationException,
            IOException, SAXException {
    };

    /**
     * Method called by the controller after a click on the button "Load distribution"
     * @param c the controler
     * @param w the window
     */
    public default void loadDistribution(Controller c, Window w) throws XMLException, ParserConfigurationException,
            IOException, SAXException{
    };

    default void computeTour(Controller controller, Window window){};

    public default void keyStroke(MapView mapView, int keyCode){};

    /**
     * Method called by the controller after a click on the button "Undo"
     * @param l the current list of commands
     */
    public default void undo(ListOfCommands l) {};

    /**
     * Method called by the controller after a click on the button "Redo"
     * @param l the current list of commands
     */
    public default void redo(ListOfCommands l) {};
}