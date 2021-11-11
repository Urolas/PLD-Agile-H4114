/**
 * Window
 *
 * @author 4IF-4114
 */
package view;

import controller.Controller;
import model.CityMap;

import java.awt.*;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * The current window of the application
 */
public class Window extends JFrame {


    // Titles of window buttons
    protected static final String LOAD_CITY_MAP = "Load a city map";
    protected static final String LOAD_DISTRIBUTION = "Load a distribution";
    protected static final String COMPUTE_TOUR = "Compute a tour";
    protected static final String MODIFY = "Add request";
    protected static final String REMOVE = "Remove";
    protected static final String REDO = "Redo";
    protected static final String UNDO = "Undo";
    protected static final String GENERATE_ROADMAP = "Generate roadmap";
    protected static final String UP = "up";
    protected static final String DOWN = "down";

    protected static final String ZOOM_IN = "+";
    protected static final String ZOOM_OUT = "-";
    protected static final String RECENTER = "=";

    private ArrayList<JButton> buttons;
    private ButtonListener buttonListener;

    private final String[] buttonTexts = new String[]{LOAD_CITY_MAP, LOAD_DISTRIBUTION, COMPUTE_TOUR, MODIFY, REMOVE, UNDO, REDO, GENERATE_ROADMAP};
    private final String[] buttonTextsZoom = new String[]{ZOOM_IN, ZOOM_OUT, RECENTER};
    private final JTextPane messageFrame;
    private final JPanel helpPanel;
    private final JTextField durationJText;
    private final MapView mapView;
    private final RoadmapView roadmapView;
    private final int BUTTON_HEIGHT = 60;
    private final int BUTTON_WIDTH = 200;

    /**
     * Constructor of Window
     *
     * @param cityMap    the current citymap
     * @param controller the controller for every action performed
     */
    public Window(CityMap cityMap, Controller controller) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            this.parsingError("Impossible to load the OS theme, theme is set to the default one");
        }
        setLayout(null);
        mapView = new MapView(cityMap, this);
        messageFrame = new JTextPane();
        durationJText = new JTextField(50);
        durationJText.setEnabled(false);
        durationJText.setVisible(false);
        roadmapView = new RoadmapView(cityMap, this);
        helpPanel = new JPanel(null);
        MouseListener mouseListener = new MouseListener(controller, mapView, this);

        messageFrame.setBorder(BorderFactory.createTitledBorder("Messages"));
        messageFrame.setFont(new Font("Segoe UI", Font.BOLD, 13));
        this.displayMessage("Please load a citymap.");
        messageFrame.setBounds(10, 10, 280, 80);
        messageFrame.setEditable(false);
        StyledDocument document = messageFrame.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        document.setParagraphAttributes(0, document.getLength(), center, false);

        helpPanel.add(messageFrame);
        helpPanel.add(durationJText);
        getContentPane().add(helpPanel);

        JLabel Title = new JLabel("Delivelo");
        Title.setFont(new Font("Segoe UI", Font.PLAIN, 30));
        Title.setBounds(50, 20, 180, 30);
        getContentPane().add(Title);

        createButtons(controller);
        addMouseListener(mouseListener);
        addMouseWheelListener(mouseListener);
        addMouseMotionListener(mouseListener);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        setWindowSize();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }


    /**
     * Create buttons corresponding to buttonTexts, and the corresponding button listener
     *
     * @param controller the current controller for performed actions
     */
    private void createButtons(Controller controller) {
        buttonListener = new ButtonListener(controller);
        buttons = new ArrayList<>();
        for (int i = 0; i < buttonTexts.length; i++) {
            JButton button = new JButton(buttonTexts[i]);
            buttons.add(button);
            button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            button.setForeground(Color.BLACK);
            button.setBounds(10, 100 + (BUTTON_HEIGHT + 10) * i, BUTTON_WIDTH - 20, BUTTON_HEIGHT);
            button.addActionListener(buttonListener);
            getContentPane().add(button);

        }
        for (int i = 0; i < buttonTextsZoom.length; i++) {
            JButton button = new JButton(buttonTextsZoom[i]);
            buttons.add(button);
            button.setSize(30, 30);
            button.setMargin(new Insets(0, 0, 5, 0));
            button.setFont(new Font("Segoe UI", Font.BOLD, 25));
            button.setLocation(mapView.getViewWidth() - 60, mapView.getViewHeight() - 170 + i * 40);
            button.addActionListener(buttonListener);
            mapView.add(button);
        }
    }

    /**
     * show the error on the JOptionPane
     *
     * @param message the error message
     */
    public void parsingError(String message) {
        final JPanel panel = new JPanel();
        JOptionPane.showMessageDialog(panel, message, "Error", JOptionPane.ERROR_MESSAGE);
    }


    public void displayMessage(String m) {
        messageFrame.setText(m);
    }

    /**
     * Adapt the size of every elements (buttons, map) to the size of the window
     */

    private void setWindowSize() {
        int allBUTTON_HEIGHT = BUTTON_HEIGHT * buttonTexts.length;
        int windowHeight = Math.max(mapView.getViewHeight(), allBUTTON_HEIGHT);
        int windowWidth = mapView.getViewWidth() + BUTTON_WIDTH + roadmapView.getViewWidth() + 15;
        setSize(windowWidth, windowHeight);
        mapView.setLocation(BUTTON_WIDTH, 0);
        roadmapView.setLocation(mapView.getViewWidth() + BUTTON_WIDTH, 160);
        helpPanel.setBounds(mapView.getViewWidth() + BUTTON_WIDTH, 0, 300, 160);
        durationJText.setBounds(75, 110, 150, 30);

        mapView.setLocation(BUTTON_WIDTH, 0);
    }

    public MapView getMapView() {
        return mapView;
    }

    public ButtonListener getButtonListener() {
        return buttonListener;
    }

    /**
     * Convert the Jtext duration to a string and empty it
     *
     * @return the converted duration as a string
     */
    public String getDuration() {
        String result = durationJText.getText();
        durationJText.setText("");
        return result;

    }

    /**
     * Disable/Enable a button if it should be clickable or not (wrong/right state)
     *
     * @param buttonLabel the name of the button
     * @param bool        change the status of a button
     */
    public void enableButton(String buttonLabel, boolean bool) {
        int i = 0;
        for (String text : buttonTexts) {
            if (text.equals(buttonLabel)) {
                this.buttons.get(i).setEnabled(bool);
            }
            i += 1;
        }
    }


    public void resetDurationInserted() {
        durationJText.setText("300");
    }

    /**
     * Activate or desactivate the text field
     *
     * @param bool enable the field where the duration is inserted
     */
    public void enableJtextField(boolean bool) {
        this.durationJText.setEnabled(bool);
        this.durationJText.setVisible(bool);
    }
}

