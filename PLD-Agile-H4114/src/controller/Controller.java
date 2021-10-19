package controller;

import java.util.*;
import model.CityMap;
import view.Window;

/**
 * @author 4IF-4114
 */
public class Controller {
    private Window window;
    private CityMap cityMap;
    /**
     * Default constructor
     */
    public Controller(CityMap cityMap) {
        this.cityMap = cityMap;
        window = new Window(cityMap,this);

    }


    /**
     * 
     */
    public ListOfCommands listOfCommands;

    /**
     * 
     */
    public State currentState;

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

}