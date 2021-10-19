package controller;

import java.util.*;

import model.CityMap;
import view.Window;

/**
 * @author 4IF-4114
 */
public class Controller {

    private CityMap cityMap;
    private Window window;
    private ListOfCommands listOfCommands;
    private State currentState;

    /**
     * Default constructor
     */
    public Controller(CityMap cityMap, int scale) {
        this.cityMap = cityMap;
        listOfCommands = new ListOfCommands();
//        currentState = initialState;
        window = new Window(cityMap, scale, this);

    }


    /**
     * 
     */

    /**
     * 
     */

    /**
     * 
     */
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

    public void loadCityMap() {
    }

    public void loadDistribution() {
    }

    public void keystroke(int keyCode) {
    }
}