package view;

import controller.Controller;
import model.CityMap;

import java.awt.*;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.border.Border;
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

    protected static final String GENERATE_ROADMAP = "Generate roadmap";

    protected static final String ZOOM_IN = "+";
    protected static final String ZOOM_OUT = "-";
    protected static final String RECENTER = "=";
    protected static final String ADD_DURATION = "Add duration";


    private final String[] buttonTexts = new String[]{LOAD_CITY_MAP, LOAD_DISTRIBUTION, COMPUTE_TOUR, MODIFY, REMOVE,UNDO,REDO,GENERATE_ROADMAP};

    private final String[] buttonTextsZoom = new String[]{ZOOM_IN,ZOOM_OUT,RECENTER};

    private JLabel messageFrame;
    private JTextField durationJText;

    private MapView mapView;
    private RoadmapView roadmapView;
    private ArrayList<JButton> buttons;

    private ButtonListener buttonListener;
    private MouseListener mouseListener;
    private KeyboardListener keyboardListener;

    private final int BUTTON_HEIGHT = 60;
    private final int BUTTON_WIDTH = 200;

    /**
     * Default constructor
     */
    public Window(CityMap cityMap, Controller controller) {
        setLayout(null);
        messageFrame = new JLabel();
        messageFrame.setBorder(BorderFactory.createTitledBorder("Messages..."));
        durationJText = new JTextField(50);


        getContentPane().add(messageFrame);
        getContentPane().add(durationJText);

        mapView = new MapView(cityMap, this);

        JLabel jl = new JLabel("Delivelo");
        jl.setFont(new Font("Segoe UI", Font.BOLD, 30));
        jl.setBounds(20,20,180,30);
        getContentPane().add(jl);

        createButtons(controller);
        roadmapView = new RoadmapView(cityMap, this);
        mouseListener = new MouseListener(controller, mapView, this);
        keyboardListener = new KeyboardListener(controller);
        addMouseListener((java.awt.event.MouseListener) mouseListener);
        addMouseWheelListener((MouseWheelListener) mouseListener);
        addMouseMotionListener((MouseMotionListener) mouseListener);

        addKeyListener(keyboardListener);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(Color.WHITE);
        setWindowSize();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void setWindowSize() {
        int allBUTTON_HEIGHT = BUTTON_HEIGHT * buttonTexts.length;
        int windowHeight = Math.max(mapView.getViewHeight(),allBUTTON_HEIGHT);
        int windowWidth = mapView.getViewWidth() + BUTTON_WIDTH + roadmapView.getViewWidth() + 15;
        setSize(windowWidth, windowHeight);
        mapView.setLocation(BUTTON_WIDTH, 0);
        roadmapView.setLocation(mapView.getViewWidth() + BUTTON_WIDTH,0);
        messageFrame.setSize(200,60);
        messageFrame.setLocation(0,windowHeight-100);
        durationJText.setBounds(windowWidth-290,windowHeight-85,150,30);

        mapView.setLocation(BUTTON_WIDTH, 0);
        roadmapView.setLocation(mapView.getViewWidth() + BUTTON_WIDTH,0);
    }

    /**
     * Create buttons corresponding to buttonTexts, and the corresponding button listener
     * @param controller
     */
    private void createButtons(Controller controller){
        buttonListener = new ButtonListener(controller);
        buttons = new ArrayList<JButton>();
        for ( int i=0; i<buttonTexts.length; i++ ) {
            JButton button = new JButton(buttonTexts[i]);
            buttons.add(button);
//            button.setBorderPainted(false);
            button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            button.setForeground(Color.BLACK);
            button.setBackground(new Color(91, 138, 231));
            button.setBounds(10,150+(BUTTON_HEIGHT+10)*i,BUTTON_WIDTH-20,BUTTON_HEIGHT);
//            button.setFocusPainted(false);
            button.addActionListener(buttonListener);
            getContentPane().add(button);

        }
        for ( int i=0; i<buttonTextsZoom.length; i++ ){
            JButton button = new JButton(buttonTextsZoom[i]);
            buttons.add(button);
            button.setSize(30,30);
            button.setMargin(new Insets(0,0,5,0));
            button.setBorderPainted(false);
            button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            button.setBackground(Color.WHITE);
            button.setOpaque(true);
            button.setLocation( mapView.getViewWidth() - 60, mapView.getViewHeight() - 170 + i * 40);
            button.setFocusable(false);
            button.setFocusPainted(false);
            button.addActionListener(buttonListener);
            mapView.add(button);
        }
        JButton buttonDuration = new JButton(ADD_DURATION);
        buttons.add(buttonDuration);
        buttonDuration.setBounds(mapView.getViewWidth() + BUTTON_WIDTH + 185, mapView.getViewHeight() - 85 , 100, 30);

        getContentPane().add(buttonDuration);
    }

    public MapView getMapView() {
        return mapView;
    }

    public ButtonListener getButtonListener() {
        return buttonListener;
    }

    public String getDuration() {
        return durationJText.getText();
    }
    public RoadmapView getRoadmapView() { return roadmapView; }

    public void parsingError(String message) {
        final JPanel panel = new JPanel();
        JOptionPane.showMessageDialog(panel, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void displayMessage(String m) {
        messageFrame.setText(m);
    }

    public void enableButton(String buttonLabel, boolean bool) {
        int i = 0;
        for (String text : buttonTexts) {
            if (text.equals(buttonLabel)) {
                this.buttons.get(i).setEnabled(bool);
            }
            i += 1;
        }
    }

    public String[] getButtonTexts() {
        return buttonTexts;
    }
}