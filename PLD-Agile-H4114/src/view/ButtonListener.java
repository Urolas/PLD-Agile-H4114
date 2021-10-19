package view;

import java.io.IOException;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.Controller;
import org.xml.sax.SAXException;
import xml.XMLException;

import javax.xml.parsers.ParserConfigurationException;

/**
 * @author 4IF-4114
 */
public class ButtonListener implements ActionListener {

    private Controller controller;

    /**
     * Default constructor
     */
    public ButtonListener(Controller controller){
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Method called by the button listener each time a button is clicked
        // Forward the corresponding message to the controller

        switch (e.getActionCommand()){
            case Window.LOAD_CITY_MAP: controller.loadCityMap(); break;
            case Window.LOAD_DISTRIBUTION: controller.loadDistribution(); break;
            case Window.UNDO: controller.undo(); break;
            case Window.REDO: controller.redo(); break;
            // TODO
        }
    }

}