package view;

import javax.swing.JLabel;

import model.CityMap;
import observer.Observable;
import observer.Observer;

public class LogView extends JLabel implements Observer {

    private static final long serialVersionUID = 1L;
    private String text;
    private CityMap cityMap;

    public LogView(CityMap plan, Window window) {
        super();
//        setBorder(BorderFactory.createTitledBorder("List of shapes:"));
        this.setVerticalTextPosition(TOP);
        this.setVerticalAlignment(TOP);
        window.getContentPane().add(this);
        plan.addObserver(this); // this observes cityMap
    }


        @Override
    public void update(Observable observed, Object object) {

    }
}
