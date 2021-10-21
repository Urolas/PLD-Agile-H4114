package view;

import controller.Controller;
import model.CityMap;

import java.awt.event.MouseMotionListener;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JButton;

import model.CityMap;
/**
 * @author 4IF-4114
 */
public class Window extends JFrame {

    // Titles of window buttons
    protected static final String LOAD_CITY_MAP = "Load a city map";
    protected static final String LOAD_DISTRIBUTION = "Load a distribution";
    protected static final String REDO = "Redo";
    protected static final String UNDO = "Undo";

    private final String[] buttonTexts = new String[]{LOAD_CITY_MAP, LOAD_DISTRIBUTION,
            UNDO, REDO};

    private MapView mapView;
    private RoadmapView roadmapView;
    private ArrayList<JButton> buttons;

    private ButtonListener buttonListener;
    private MouseListener mouseListener;
    private KeyboardListener keyboardListener;

    private final int buttonHeight = 40;
    private final int buttonWidth = 150;

    /**
     * Default constructor
     */

    public Window(CityMap cityMap, Controller controller) {
        setLayout(null);
        createButtons(controller);
        mapView = new MapView(cityMap, this);
        roadmapView = new RoadmapView(cityMap, this);
        mouseListener = new MouseListener(controller, mapView, this);
        keyboardListener = new KeyboardListener(controller);
        addMouseListener((java.awt.event.MouseListener) mouseListener);
        addMouseMotionListener((MouseMotionListener) mouseListener);
        addKeyListener(keyboardListener);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setWindowSize();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setWindowSize() {
        int allButtonHeight = buttonHeight*buttonTexts.length;
        int windowHeight = Math.max(mapView.getViewHeight(),allButtonHeight);
        int windowWidth = mapView.getViewWidth()+buttonWidth+10;
        setSize(windowWidth, windowHeight);
        mapView.setLocation(buttonWidth, 0);
//        roadmapView.setSize(textualViewWidth,windowHeight-messageFrameHeight);
//        roadmapView.setLocation(10+graphicalView.getViewWidth()+buttonWidth,0);
    }

    /**
     * Create buttons corresponding to buttonTexts, and the corresponding button listener
     * @param controller
     */
    private void createButtons(Controller controller){
        buttonListener = new ButtonListener(controller);
        buttons = new ArrayList<JButton>();
        for (String text : buttonTexts){
            JButton button = new JButton(text);
            buttons.add(button);
            button.setSize(buttonWidth,buttonHeight);
            button.setLocation(0,(buttons.size()-1)*buttonHeight);
            button.setFocusable(false);
            button.setFocusPainted(false);
            button.addActionListener(buttonListener);
            getContentPane().add(button);
        }
    }
}