/**
 * KeyboardListener
 *
 * @author 4IF-4114
 */
package view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import controller.Controller;


/**
 * ActionListener for key-pressed
 */
public class KeyboardListener extends KeyAdapter {

    private final Controller controller;

    /**
     * Constructor of KeyboardListener
     * @param controller controller the current controller which will receive the keyCode message
     */
    public KeyboardListener(Controller controller) {
        this.controller = controller;
    }

    /**
     * Method called by the keyboard listener each time a key is pressed
     * @param e the keyEvent indicating which key is pressed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        controller.keystroke(e.getKeyCode());

    }
}