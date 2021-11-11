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

    /**
     * Method called when the user wants to generate a roadMap
     * @param c the current controller
     * @param w the current window
     * @throws IOException
     */
    default void generateRoadmap(Controller c, Window w) throws IOException {}

    /**
     * Method called when the user wants to compute a tour
     * @param controller the current controller
     * @param window the current window
     */
    default void computeTour(Controller controller, Window window){}

    /**
     * Method called when the user wants to add a new request
     * @param controller the current controller
     */
    default void addRequest(Controller controller){}

    /**
     * Method called when the user wants to remove a point of interest
     * @param c the current controller
     * @param w the current window
     * @param map the cityMap
     * @param listOfCommands the list of command to store the action
     */
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

    /**
     * To activate/deactivate the button depending on the state
     * @param window the current window
     * @param listOfCommands the list of command (required for some states)
     */
    default void enableButtons(Window window, ListOfCommands listOfCommands) {}

    /**
     * Method called by the controller after a right click, used to cancel a modification
     * @param controller the current controller
     */
    default void rightClick(Controller controller) {}

    /**
     * Method called when the user click on the "Up arrow" button, performs a switch in the roadmap order between the above
     * point of interest and the current one
     * @param id an id used to identify the clicked point of interest
     * @param listOfCommands the list of command used to store the modification
     * @param controller the current controller
     */
    default void up(Integer id, ListOfCommands listOfCommands, Controller controller) {}

    /**
     * Method called when the user click on the "Down arrow" button, performs a switch in the roadmap order between the below
     * point of interest and the current one
     * @param id an id used to identify the clicked point of interest
     * @param listOfCommands the list of command used to store the modification
     * @param controller the current controller
     */
    default void down(Integer id, ListOfCommands listOfCommands, Controller controller) {}

    /**
     * Method called when the user when to add a request in the MapView (with his mouse),
     * especially to display the point of interest to add
     * @param controller the current controller
     * @param intersection the intersection to add and display
     */
    default void mouseMoved(Controller controller, Intersection intersection) {}
}