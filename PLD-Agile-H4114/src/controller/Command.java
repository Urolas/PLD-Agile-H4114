package controller;


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