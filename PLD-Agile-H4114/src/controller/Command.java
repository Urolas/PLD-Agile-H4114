package controller;


/**
 *
 */
public interface Command {

    /**
     *
     */
    void doCommand() throws Exception;

    /**
     *
     */
    void undoCommand();

}