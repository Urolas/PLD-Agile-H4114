package view;

import java.awt.*;
import java.util.*;

import model.CityMap;
import observer.Observable;
import observer.Observer;

import javax.swing.*;

/**
 * @author 4IF-4114
 */
public class MapView extends JPanel implements Observer {

    private int scale;
    private int viewHeight = 50;
    private int viewWidth = 50;
    private CityMap cityMap;

    /**
     * Default constructor
     */
    public MapView(CityMap cityMap, int s, Window w) {
        super();
        cityMap.addObserver(this); // this observes plan
        this.scale = s;
//        viewHeight = cityMap.getHeight()*s;
//        viewWidth = cityMap.getWidth()*s;
        setLayout(null);
        setBackground(Color.white);
        setSize(viewWidth, viewHeight);
        w.getContentPane().add(this);
        this.cityMap = cityMap;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg != null){ // arg is a shape that has been added to plan
            Shape s = (Shape)arg;
//            s.addObserver(this);  // this observes s
        }
        repaint();
    }

    public int getViewHeight() {
        return viewHeight;
    }

    public int getViewWidth() {
        return viewWidth;
    }
}