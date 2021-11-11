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
     * Default constructor
     */

    public Controller(CityMap city) {
        this.CITY_MAP = city;
        this.LIST_OF_COMMANDS = new ListOfCommands();
        this.currentState = INITIAL_STATE;
        this.WINDOW = new Window(CITY_MAP, this);
        currentState.enableButtons(WINDOW, LIST_OF_COMMANDS);
    }

    protected void setCurrentState(State state) {
        this.currentState = state;
        currentState.enableButtons(WINDOW, LIST_OF_COMMANDS);
    }


    /**
     * Method called by window after a click on the button "Undo"
     */
    public void undo() {
        currentState.undo(LIST_OF_COMMANDS);
        currentState.enableButtons(WINDOW, LIST_OF_COMMANDS);
    }

    /**
     * Method called by window after a click on the button "Redo"
     */
    public void redo() {
        currentState.redo(LIST_OF_COMMANDS);
        currentState.enableButtons(WINDOW, LIST_OF_COMMANDS);
    }


    public void loadCityMap() {
        try {
            this.currentState.loadMap(this, WINDOW);
        } catch (Exception e) {
            WINDOW.parsingError(e.getMessage());
        }
    }

    public void loadDistribution() {
        try {
            this.currentState.loadDistribution(this, WINDOW);
        } catch (Exception e) {
            WINDOW.parsingError(e.getMessage());
        }
    }

    public void generateRoadmap() {
        try {
            this.currentState.generateRoadmap(this, WINDOW);
        } catch (Exception e) {
            WINDOW.parsingError(e.getMessage());
        }
    }

    public CityMap getCityMap() {
        return CITY_MAP;
    }

    public void computeTour() {
        this.currentState.computeTour(this, WINDOW);
    }

    public void modifyDistribution() {
        this.currentState.modifyDistribution(this);
    }

    public void removePointOfInterest() {
        this.currentState.removePointOfInterest(this, this.WINDOW, this.CITY_MAP, this.LIST_OF_COMMANDS);
    }

    public void zoomIn() {
        WINDOW.getMapView().modifyZoom(1.5, WINDOW.getMapView().getViewWidth() / 2,
                                       WINDOW.getMapView().getViewHeight() / 2);
    }

    public void zoomOut() {
        WINDOW.getMapView().modifyZoom(1.0 / 1.5, WINDOW.getMapView().getViewWidth() / 2,
                                       WINDOW.getMapView().getViewHeight() / 2);
    }

    public void recenter() {
        WINDOW.getMapView().modifyZoom(1.0, WINDOW.getMapView().getViewWidth() / 2,
                                       WINDOW.getMapView().getViewHeight() / 2);
    }

    public void leftClick(Intersection intersection, PointOfInterest pointOfInterest) {
        currentState.leftClick(this, WINDOW, CITY_MAP, LIST_OF_COMMANDS, intersection, pointOfInterest);
    }

    public Window getWindow() {
        return WINDOW;
    }

    public void rightClick() {
        currentState.rightClick(this);
    }

    public void up(String id) {
        currentState.up(Integer.parseInt(id), this.LIST_OF_COMMANDS, this);
        currentState.enableButtons(WINDOW, LIST_OF_COMMANDS);

    }

    public void down(String id) {
        currentState.down(Integer.parseInt(id), this.LIST_OF_COMMANDS, this);
        currentState.enableButtons(WINDOW, LIST_OF_COMMANDS);

    }

    public void mouseMoved(Intersection intersection) {
        currentState.mouseMoved(this, intersection);
    }

    public void resetListOfCommands() {
        this.LIST_OF_COMMANDS.reset();
    }
}