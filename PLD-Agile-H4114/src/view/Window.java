package view;

import controller.Controller;
import java.util.*;
import javax.swing.JFrame;
import model.CityMap;
/**
 * @author 4IF-4114
 */
public class Window extends JFrame{

    private final int WIN_WIDTH = 1000;
    private final int WIN_HEIGHT = 1000;
    private MapView mapView;
    private ButtonListener buttonListener;

    /**
     * Default constructor
     */
    public Window(CityMap cityMap, Controller controller) {
        setLayout(null);

        mapView = new MapView(cityMap, this);
        setSize(WIN_WIDTH,WIN_HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);
    }






}