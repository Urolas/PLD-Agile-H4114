package controller;

import java.util.*;

/**
 * 
 */
public interface Command {

    /**
     * @return
     */
    public void doCommand();

    /**
     * @return
     */
    public void undoCommand();

}