package view;

import controller.Controller;
import model.CityMap;

import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import javax.swing.*;

/**
 * @author 4IF-4114
 */
public class Window extends JFrame {

    // Titles of window buttons
    protected static final String LOAD_CITY_MAP = "Load a city map";
    protected static final String LOAD_DISTRIBUTION = "Load a distribution";
    protected static final String COMPUTE_TOUR = "Compute a tour";
    protected static final String MODIFY = "Modify the distribution";
    protected static final String REMOVE = "Remove";
    protected static final String REDO = "Redo";
    protected static final String UNDO = "Undo";
    protected static final String ZOOM_IN = "+";
    protected static final String ZOOM_OUT = "-";
    protected static final String RECENTER = "=";

    private final String[] buttonTexts = new String[]{LOAD_CITY_MAP, LOAD_DISTRIBUTION, COMPUTE_TOUR, MODIFY, REMOVE,UNDO,REDO};
    private final String[] buttonTextsZoom = new String[]{ZOOM_IN,ZOOM_OUT,RECENTER};

    private JLabel messageFrame;

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
        messageFrame = new JLabel();
        messageFrame.setBorder(BorderFactory.createTitledBorder("Messages..."));
        getContentPane().add(messageFrame);

        mapView = new MapView(cityMap, this);
        createButtons(controller);
        roadmapView = new RoadmapView(cityMap, this);
        mouseListener = new MouseListener(controller, mapView, this);
        keyboardListener = new KeyboardListener(controller);
        addMouseListener((java.awt.event.MouseListener) mouseListener);
        addMouseWheelListener((MouseWheelListener) mouseListener);
        addMouseMotionListener((MouseMotionListener) mouseListener);

        addKeyListener(keyboardListener);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setWindowSize();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void setWindowSize() {
        int allButtonHeight = buttonHeight * buttonTexts.length;
        int windowHeight = Math.max(mapView.getViewHeight(),allButtonHeight);
        int windowWidth = mapView.getViewWidth() + buttonWidth + roadmapView.getViewWidth();
        setSize(windowWidth, windowHeight);
        messageFrame.setSize(windowWidth,60);
        messageFrame.setLocation(0,windowHeight-100);
        mapView.setLocation(buttonWidth, 0);
        roadmapView.setLocation(mapView.getViewWidth() + buttonWidth,0);
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
        for ( int i=0; i<buttonTextsZoom.length; i++ ){
            JButton button = new JButton(buttonTextsZoom[i]);
            buttons.add(button);
            button.setSize(50,50);
            button.setLocation( mapView.getViewWidth() - 190 + i * 60, mapView.getViewHeight() - 100);
            button.setFocusable(false);
            button.setFocusPainted(false);
            button.addActionListener(buttonListener);
            mapView.add(button);
        }
        System.out.println(buttons.size());

    }

    public MapView getMapView() {
        return mapView;
    }

    public void parsingError(String message) {
        final JPanel panel = new JPanel();
        JOptionPane.showMessageDialog(panel, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    public void displayMessage(String m) {
        messageFrame.setText(m);
    }

}