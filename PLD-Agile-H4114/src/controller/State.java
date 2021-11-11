package controller;

import filecontrol.XMLException;
import model.CityMap;
import model.Intersection;
import model.PointOfInterest;
import org.xml.sax.SAXException;
import view.Window;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * @author 4IF-4114
 */
public interface State {


    /**
     * Method called by the controller after a click on the button "Load a map"
     * @param controller the controller
     * @param window the window
     */
    default void loadMap(Controller controller, Window window) throws XMLException, ParserConfigurationException,
            IOException, SAXException {
    }

    /**
     * Method called by the controller after a click on the button "Load distribution"
     * @param controller the controller
     * @param window the window
     */
    default void loadDistribution(Controller controller, Window window) throws XMLException, ParserConfigurationException,
            IOException, SAXException{
    }

    default void generateRoadmap(Controller c, Window w) throws IOException {}

    default void computeTour(Controller controller, Window window){}

    default void addRequest(Controller controller){}

    default void removePointOfInterest(Controller c, Window w, CityMap map, ListOfCommands listOfCommands){}

    /**
     * Method called by the controller after a click on the button "Undo"
     * @param l the current list of commands
     */
    default void undo(ListOfCommands l) {}

    /**
     * Method called by the controller after a click on the button "Redo"
     * @param l the current list of commands
     */
    default void redo(ListOfCommands l) {}

    /**
     * Method called by the controller after a left click
     * Precondition : p != null
     * @param controller the controller
     * @param window the window
     * @param cityMap the citymap
     * @param listOfCommands the current list of commands
     * @param pointOfInterest the coordinates of the mouse
     */
    default void leftClick(Controller controller, Window window, CityMap cityMap, ListOfCommands listOfCommands,
                           Intersection intersection, PointOfInterest pointOfInterest) {}


    default void enableButtons(Window window, ListOfCommands listOfCommands) {}

    default void rightClick(Controller controller) {}

    default void up(Integer id, ListOfCommands listOfCommands, Controller controller) {}

    default void down(Integer id, ListOfCommands listOfCommands, Controller controller) {}

    default void mouseMoved(Controller controller, Intersection intersection) {}
}