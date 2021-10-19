package view;

import java.awt.Graphics;
import java.util.*;
import javax.swing.JPanel;
import model.CityMap;

/**
 * @author 4IF-4114
 */
public class MapView extends JPanel implements Observer {
    private Graphics g;
    /**
     * Default constructor
     */
    public MapView(CityMap cityMap, Window window) {
        super();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

    }

    /**
     * @param observed 
     * @param object 
     * @return
     */
    public void update(Observable observed, Object object) {
        // TODO implement here
    }

    public void displayRoad(){

    }
    public void displayIntersection(){

    }

}