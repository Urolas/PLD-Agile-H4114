/**
 * MouseListener
 * @author 4IF-4114
 */
package view;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import javax.swing.SwingUtilities;

import controller.Controller;
import model.Intersection;
import model.PointOfInterest;

/**
 * ActionListener for mouse-movement and clicks
 */
public class MouseListener extends MouseAdapter {

    private final Controller controller;
    private final MapView mapView;
    private final Window window;

    /**
     * Constructor of MouseListener
     * @param c the current controller that will receive messages
     * @param mapView the clickable view of the map
     * @param w the application Window
     */
    public MouseListener(Controller c, MapView mapView, Window w){
        this.controller = c;
        this.mapView = mapView;
        this.window = w;
    }


    /**
     * Perform an action when the mouse is click
     * @param evt the mouse event from the window
     */
    @Override
    public void mouseClicked(MouseEvent evt){
        MouseEvent e = SwingUtilities.convertMouseEvent(window, evt, mapView);
        switch (e.getButton()) {
            case MouseEvent.BUTTON1 -> {
                Intersection intersection = mapView.getClosestIntersection(e.getX(), e.getY());

                PointOfInterest pointOfInterest = mapView.getClosestPointOfInterest(e.getX(), e.getY());
                controller.leftClick(intersection, pointOfInterest);
            }
            case MouseEvent.BUTTON3 -> controller.rightClick();
            default -> {
            }
        }
    }

    /**
     * Perform an action when the mouse is pressed
     * @param evt the mouse event from the window
     */
    @Override
    public void mousePressed(MouseEvent evt) {
        MouseEvent e = SwingUtilities.convertMouseEvent(window, evt, mapView);
        if (e.getX() >= 0){
            mapView.setMouseClickedX(e.getX());
            mapView.setMouseClickedY(e.getY());
            mapView.fixOrigin();
        }
    }

    /**
     * Perform an action when the mouse is dragged
     * @param evt the mouse event from the window
     */
    @Override
    public void mouseDragged(MouseEvent evt){
        MouseEvent e = SwingUtilities.convertMouseEvent(window, evt, mapView);
        if (e.getX() >= 0){
            mapView.dragMap(e.getX(),e.getY());
        }
    }

    /**
     * Perform an action when the mousewheel is moved
     * @param e the mousewheel event
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e){
        int scrollDirection = e.getWheelRotation();
        MouseEvent ev = SwingUtilities.convertMouseEvent(window, e, mapView);
        int mouseX = ev.getX();
        int mouseY = ev.getY();
        if (mouseX >= 0 && mouseX <= mapView.getViewWidth() && mouseY >= 0 && mouseY <= mapView.getViewHeight()){
            if (scrollDirection > 0){
                mapView.modifyZoom(1/1.2,mouseX, mouseY);
            } else if (scrollDirection < 0){
                mapView.modifyZoom(1.2, mouseX, mouseY);
            }
        }


    }

    /**
     * Perform an action when the mouse is moved
     * @param evt the mouse event from the window
     */
    @Override
    public void mouseMoved(MouseEvent evt) {
        MouseEvent e = SwingUtilities.convertMouseEvent(window, evt, mapView);
        Intersection intersection = mapView.getClosestIntersection(e.getX(), e.getY());
        controller.mouseMoved(intersection);
    }
}