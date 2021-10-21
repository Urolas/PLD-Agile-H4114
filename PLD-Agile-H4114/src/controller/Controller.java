package controller;

import model.CityMap;
import view.Window;

/**
 * @author 4IF-4114
 */
public class Controller {
    // Instances associated with each possible state of the controller
    protected final InitialState initialState = new InitialState();
    protected final CityMapState citymapState = new CityMapState();
    protected final DistributionState distributionState = new DistributionState();
    protected final TourState tourState = new TourState();

    private CityMap cityMap;
    private Window window;
    private ListOfCommands listOfCommands;
    private State currentState;

    /**
     * Default constructor
     */

    public Controller(CityMap city) {
        this.cityMap = city;
        this.listOfCommands = new ListOfCommands();
        this.currentState = initialState;
        this.window = new Window(cityMap,this);
    }

    protected void setCurrentState(State state){
        this.currentState = state;
    }

    /**
     * @return
     */
    public void undo() {
        // TODO implement here
    }

    /**
     * @return
     */
    public void redo() {
        // TODO implement here
    }

    /**
     * @param command 
     * @return
     */
    public void add(Command command) {
        // TODO implement here
    }

    public void loadCityMap()  {
        try{
            this.currentState.loadMap(this, window);
        }catch(Exception e){

        }
    }

    public void loadDistribution(){
        try{
            this.currentState.loadDistribution(this,window);
        }catch(Exception e){

        }
    }

    public void keystroke(int keyCode) {

    }

    public CityMap getCitymap() {
        return cityMap;
    }
}