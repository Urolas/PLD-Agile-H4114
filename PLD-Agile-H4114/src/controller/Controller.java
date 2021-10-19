package controller;
import model.*;

import java.util.*;
import view.Window;

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
    protected final CityMapState1 citymapState = new CityMapState1();

    public Controller(CityMap city) {
        this.citymap = city;
        this.listOfCommands = new ListOfCommands();
        this.currentState = initialState;
        this.window = new Window(this);
    }

    public void loadMap(){

    }

    public void loadDistribution(){

    }


    public void ListOfCommands() {
        // TODO implement here
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

}