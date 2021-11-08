package controller;


/**
 * 
 */
public interface Command {

    /**
     * @return
     */
    public void doCommand() throws Exception;

    /**
     * @return
     */
    public void undoCommand();

}