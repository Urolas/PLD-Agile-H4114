package view;

import controller.Controller;
import java.util.*;
import javax.swing.JFrame;
/**
 * @author 4IF-4114
 */
public class Window extends JFrame{

    private final int WIN_WIDTH = 1000;
    private final int WIN_HEIGHT = 1000;
    private MapView graphicalView;
    private ButtonListener buttonListener;

    /**
     * Default constructor
     */
    public Window(Controller controller) {
        setLayout(null);

        graphicalView = new MapView();
        setSize(WIN_WIDTH,WIN_HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);
    }






}