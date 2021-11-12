/**
 * Observable
 * @author 4IF-4114
 */
package observer;

import java.util.HashSet;
import java.util.Set;
/**
 * Creates Observer subclasses to represent an object that the application wants to have observed
 */
public class Observable {

    private Set<Observer> observers;
    /**
     * Constructor of Observable
     */
    public Observable() {
        this.observers = new HashSet<Observer>();
    }

    /**
     * Add an observer to the list of observers
     * @param obs the new observer to be added
     */
    public void addObserver(Observer obs){
        if (!this.observers.contains(obs)) observers.add(obs);
    }

    /**
     * Notify if there's a change on the object
     * @param arg the modified object
     */
    public void notifyObservers(Object arg){
        for(Observer o : this.observers)
            o.update(this,arg);
    }

    /**
     * Notify that nothing has been modified
     */
    public void notifyObservers(){
        notifyObservers(null);
    }

}