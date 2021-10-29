package view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

import controller.Controller;

/**
 * @author 4IF-4114
 */
public class MouseListener extends MouseAdapter {

    private Controller controller;
    private MapView mapView;
    private RoadmapView roadMapView;
    private Window window;

    /**
     * Default constructor
     */
    public MouseListener(Controller c, MapView mapView, Window w){
        this.controller = c;
        this.mapView = mapView;
        this.window = w;
    }

    @Override
    public void mousePressed(MouseEvent evt) {
        MouseEvent e = SwingUtilities.convertMouseEvent(window, evt, mapView);
        if (e.getX() >= 0){
            mapView.setMouseClickedX(e.getX());
            mapView.setMouseClickedY(e.getY());
            mapView.fixOrigin();
        }
        System.out.println(e.getX());
    }

    @Override
    public void mouseDragged(MouseEvent evt){
        MouseEvent e = SwingUtilities.convertMouseEvent(window, evt, mapView);
        if (e.getX() >= 0){
            mapView.dragMap(e.getX(),e.getY());
        }
        System.out.println(e.getX());
    }



}