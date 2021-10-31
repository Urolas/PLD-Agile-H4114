package view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import controller.Controller;


/**
 * @author 4IF-4114
 */
public class KeyboardListener extends KeyAdapter {

    private Controller controller;

    /**
     * Default constructor
     * @param controller
     */
    public KeyboardListener(Controller controller){
        this.controller = controller;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Method called by the keyboard listener each time a key is pressed
        controller.keystroke(e.getKeyCode());



    }
}