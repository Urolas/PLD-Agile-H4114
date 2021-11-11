/**
 * Observer
 * @author 4IF-4114
 */
package observer;

/**
 * An Observer sub-class to notify the observable when an object is modified
 */
public interface Observer {

    /**
     * Update other objects or views related to the modified object
     * @param observed the Observable
     * @param object the modified object
     */
    public void update(Observable observed, Object object);

}