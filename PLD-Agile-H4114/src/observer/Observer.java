package observer;



/**
 * 
 */
public interface Observer {

    /**
     * @param observed 
     * @param object 
     * @return
     */
    public void update(Observable observed, Object object);

}