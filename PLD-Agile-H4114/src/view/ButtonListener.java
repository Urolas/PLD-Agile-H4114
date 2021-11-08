package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.Controller;

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

            case Window.GENERATE_ROADMAP: controller.generateRoadmap(); break;

            case Window.COMPUTE_TOUR: controller.computeTour();break;
            case Window.MODIFY: controller.modifyDistribution();break;
            case Window.REMOVE: controller.removePointOfInterest();break;

            case Window.ZOOM_IN: controller.zoomIn();break;
            case Window.ZOOM_OUT: controller.zoomOut();break;
            case Window.RECENTER: controller.recenter();break;
            // TODO
        }
    }

}