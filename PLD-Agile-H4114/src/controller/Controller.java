/**
 * Controller
 *
 * @author 4IF-4114
 */
package controller;

import model.CityMap;

import model.Intersection;
import model.PointOfInterest;

import view.Window;

/**
 * Control the input of the user
 */
public class Controller {
    // Instances associated with each possible state of the controller
    protected final InitialState INITIAL_STATE = new InitialState();
    protected final CityMapState CITY_MAP_STATE = new CityMapState();
    protected final DistributionState DISTRIBUTION_STATE = new DistributionState();
    protected final TourState TOUR_STATE = new TourState();
    protected final AddState1 ADD_STATE_1 = new AddState1();
    protected final AddState2 ADD_STATE_2 = new AddState2();
    protected final AddState3 ADD_STATE_3 = new AddState3();
    protected final AddState4 ADD_STATE_4 = new AddState4();
    protected final HighlightState HIGHLIGHT_STATE = new HighlightState();

    private final CityMap CITY_MAP;
    private final Window WINDOW;
    private final ListOfCommands LIST_OF_COMMANDS;
    private State currentState;

    /**
     * Constructor of Controller
     *
     * @param city the city map
     */
    public Controller(CityMap city) {
        this.CITY_MAP = city;
        this.LIST_OF_COMMANDS = new ListOfCommands();
        this.currentState = INITIAL_STATE;
        this.WINDOW = new Window(CITY_MAP, this);
        currentState.enableButtons(WINDOW, LIST_OF_COMMANDS);
    }

    /**
     * Change the state of the controller
     */
    protected void setCurrentState(State state) {
        this.currentState = state;
        currentState.enableButtons(WINDOW, LIST_OF_COMMANDS);
    }


    /**
     * calls the current state to undo the last command performed, called by the button "undo"
     */
    public void undo() {
        currentState.undo(LIST_OF_COMMANDS);
        currentState.enableButtons(WINDOW, LIST_OF_COMMANDS);
    }

    /**
     * calls the current state to redo the last command performed, called by the button "redo"
     */
    public void redo() {
        currentState.redo(LIST_OF_COMMANDS);
        currentState.enableButtons(WINDOW, LIST_OF_COMMANDS);
    }

    /**
     * calls the current state to load the city map, called by the button "load a city map"
     */
    public void loadCityMap() {
        try {
            this.currentState.loadMap(this, WINDOW);
        } catch (Exception e) {
            WINDOW.parsingError(e.getMessage());
        }
    }

    /**
     * calls the current state to load the distribution, called by the button "load a distribution"
     */
    public void loadDistribution() {
        try {
            this.currentState.loadDistribution(this, WINDOW);
        } catch (Exception e) {
            WINDOW.parsingError(e.getMessage());
        }
    }

    /**
     * calls the current state to generate a roadMap, called by the button "generate a roadmap"
     */
    public void generateRoadmap() {
        try {
            this.currentState.generateRoadmap(this, WINDOW);
        } catch (Exception e) {
            WINDOW.parsingError(e.getMessage());
        }
    }

    /**
     * calls the compute the tour, called by the button "compute the tour"
     */
    public void computeTour() {
        this.currentState.computeTour(this, WINDOW);
    }

    /**
     * calls the current state to enter in the modification mode, called by the button "add request"
     */
    public void addRequest() {
        this.currentState.addRequest(this);
    }

    /**
     * calls the current state to remove a request, called by the button "remove"
     */
    public void removePointOfInterest() {
        this.currentState.removePointOfInterest(this, this.WINDOW, this.CITY_MAP, this.LIST_OF_COMMANDS);
    }

    /**
     * calls the current state to zoom in, called by the mouse wheel
     */
    public void zoomIn() {
        WINDOW.getMapView().modifyZoom(1.5, WINDOW.getMapView().getViewWidth() / 2,
                WINDOW.getMapView().getViewHeight() / 2);
    }

    /**
     * calls the current state to zoom out, called by the mouse wheel
     */
    public void zoomOut() {
        WINDOW.getMapView().modifyZoom(1.0 / 1.5, WINDOW.getMapView().getViewWidth() / 2,
                WINDOW.getMapView().getViewHeight() / 2);
    }

    /**
     * calls the current state to recenter the map, called by the "=" button
     */
    public void recenter() {
        WINDOW.getMapView().modifyZoom(1.0, WINDOW.getMapView().getViewWidth() / 2,
                WINDOW.getMapView().getViewHeight() / 2);
    }

    /**
     * tells the current state that a leftclick arrived
     *
     * @param intersection    closest intersection from the click
     * @param pointOfInterest closest point of interest from the click
     */
    public void leftClick(Intersection intersection, PointOfInterest pointOfInterest) {
        currentState.leftClick(this, WINDOW, CITY_MAP, LIST_OF_COMMANDS, intersection, pointOfInterest);
    }

    /**
     * tells the current state that a rightclick arrived
     */
    public void rightClick() {
        currentState.rightClick(this);
    }

    /**
     * tells the current state that a button "▲" was pressed
     *
     * @param id the id of the point to move
     */
    public void up(String id) {
        currentState.up(Integer.parseInt(id), this.LIST_OF_COMMANDS, this);
        currentState.enableButtons(WINDOW, LIST_OF_COMMANDS);

    }

    /**
     * tells the current state that a button "▼" was pressed
     *
     * @param id the id of the point to move
     */
    public void down(String id) {
        currentState.down(Integer.parseInt(id), this.LIST_OF_COMMANDS, this);
        currentState.enableButtons(WINDOW, LIST_OF_COMMANDS);

    }

    /**
     * tells the current state that the mouse was moved
     *
     * @param intersection closest intersection from the new mouse position
     */
    public void mouseMoved(Intersection intersection) {
        currentState.mouseMoved(this, intersection);
    }

    /**
     * reset the list of commands
     */
    public void resetListOfCommands() {
        this.LIST_OF_COMMANDS.reset();
    }

    public CityMap getCityMap() {
        return CITY_MAP;
    }

    public Window getWindow() {
        return WINDOW;
    }


}