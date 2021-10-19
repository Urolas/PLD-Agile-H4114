package observer;

import java.util.*;

/**
 * @author 4IF-4114
 */
public class Observable {

    private Set<Observer> observers;
    /**
     * Default constructor
     */
    public Observable() {
        this.observers = new HashSet<Observer>();
    }
    public void addObserver(Observer obs){
        if (!this.observers.contains(obs)) observers.add(obs);
    }
    public void notifyObservers(Object arg){
        for(Observer o : this.observers)
            o.update(this,arg);
    }
    public void notifyObservers(){
        notifyObservers(null);
    }

}