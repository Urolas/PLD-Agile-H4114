package controller;
import model.*;

import java.io.IOException;
import java.util.*;

import model.CityMap;
import org.xml.sax.SAXException;
import view.Window;
import xml.XMLException;

import javax.xml.parsers.ParserConfigurationException;

/**
 * @author 4IF-4114
 */
public class Controller {
    // Instances associated with each possible state of the controller
    final InitialState initialState = new InitialState();
    final CityMapState1 citymapState1 = new CityMapState1();
    final CityMapState2 citymapState2 = new CityMapState2();
    final DistributionState1 distributionState1 = new DistributionState1();
    final DistributionState2 distributionState2 = new DistributionState2();
    final TourState tourState = new TourState();

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