package controller;

import java.util.Set;
import java.util.HashSet;


/**
 * @author 4IF-4114
 */

public class ListOfCommands {

    private Set<Command> commands;
    private int currentIndex;
    /**
     * Default constructor
     */
    public ListOfCommands() {
        currentIndex = -1;
        commands = new HashSet<Command>();
    }

    /**
     * 
     */
    public void add(Command command) {
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

}