/**
 * ButtonListener
 * @author 4IF-4114
 */
package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.Controller;

/**
 * ActionListener for button-clicks
 */
public class ButtonListener implements ActionListener {

    private final Controller controller;

    /**
     * Constructor of ButtonListener
     * @param controller the current controller which will receive the action message from the actionPerformed method
     */
    public ButtonListener(Controller controller){
        this.controller = controller;
    }

    /**
     * Method called by the button listener each time a button is clicked
     * and forward the corresponding message to the controller
     * @param e the actionEvent indicating which button is clicked
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        String actionCommand = e.getActionCommand();
         if(actionCommand.startsWith(Window.UP)){
            actionCommand = Window.UP;
        } else if(actionCommand.startsWith(Window.DOWN)){
             actionCommand = Window.DOWN;
         }
        switch (actionCommand) {
            case Window.LOAD_CITY_MAP -> controller.loadCityMap();
            case Window.LOAD_DISTRIBUTION -> controller.loadDistribution();
            case Window.UNDO -> controller.undo();
            case Window.REDO -> controller.redo();
            case Window.GENERATE_ROADMAP -> controller.generateRoadmap();
            case Window.COMPUTE_TOUR -> controller.computeTour();
            case Window.MODIFY -> controller.addRequest();
            case Window.REMOVE -> controller.removePointOfInterest();
            case Window.ZOOM_IN -> controller.zoomIn();
            case Window.ZOOM_OUT -> controller.zoomOut();
            case Window.RECENTER -> controller.recenter();
            case Window.UP -> controller.up(e.getActionCommand().substring(2));
            case Window.DOWN -> controller.down(e.getActionCommand().substring(4));
        }
    }

}