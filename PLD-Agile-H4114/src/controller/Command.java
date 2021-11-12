package controller;


/**
 *The command executed on the computed tour
 */
public interface Command {

    /**
     * do the command
     */
    void doCommand() throws Exception;

    /**
     *undo the command
     */
    void undoCommand();

}