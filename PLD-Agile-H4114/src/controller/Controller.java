package controller;
import model.*;

import java.io.IOException;
import java.util.*;

import org.xml.sax.SAXException;
import view.Window;
import xml.XMLException;

import javax.xml.parsers.ParserConfigurationException;

/**
 * @author 4IF-4114
 */
public class Controller {

    private CityMap citymap;
    public ListOfCommands listOfCommands;
    public State currentState;
    private Window window;

    // Instances associated with each possible state of the controller
    protected final InitialState initialState = new InitialState();
    protected final CityMapState1 citymapState1 = new CityMapState1();
    protected final CityMapState2 citymapState2 = new CityMapState2();
    protected final DistributionState1 distributionState1 = new DistributionState1();
    protected final DistributionState2 distributionState2 = new DistributionState2();
    protected final TourState tourState = new TourState();

    public Controller(CityMap city) {
        this.citymap = city;
        this.listOfCommands = new ListOfCommands();
        this.currentState = initialState;
        this.window = new Window(this);
    }

    protected void setCurrentState(State state){
        this.currentState = state;
    }

    public void loadMap() throws XMLException, ParserConfigurationException, IOException, SAXException {
        this.currentState.loadMap(this, window);
    }

    public void loadDistribution() throws XMLException, ParserConfigurationException, IOException, SAXException {
        this.currentState.loadDistribution(this,window);
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

    public CityMap getCitymap() {
        return citymap;
    }
}