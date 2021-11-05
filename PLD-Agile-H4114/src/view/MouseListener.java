package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
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
    public void mouseClicked(MouseEvent evt){
        MouseEvent e = SwingUtilities.convertMouseEvent(window, evt, mapView);
        double clickLong = mapView.xToLong(e.getX());
        double clickLat = mapView.yToLat(e.getY());
        controller.leftClick(clickLong,clickLat);
        controller.leftClick(new Point(clickLat, clickLong));
    }
    @Override
    public void mousePressed(MouseEvent evt) {
        MouseEvent e = SwingUtilities.convertMouseEvent(window, evt, mapView);
        if (e.getX() >= 0){
            mapView.setMouseClickedX(e.getX());
            mapView.setMouseClickedY(e.getY());
            mapView.fixOrigin();
        }
    }

    @Override
    public void mouseDragged(MouseEvent evt){
        MouseEvent e = SwingUtilities.convertMouseEvent(window, evt, mapView);
        if (e.getX() >= 0){
            mapView.dragMap(e.getX(),e.getY());
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e){
        int scrollDirection = e.getWheelRotation();
        MouseEvent ev = SwingUtilities.convertMouseEvent(window, e, mapView);
        System.out.println("MouseWheelMoved");

        int mouseX = ev.getX();
        int mouseY = ev.getY();
        if (scrollDirection > 0){
            mapView.modifyZoom(1/1.2,mapView.getViewWidth()/2, mapView.getViewHeight()/2);
        } else if (scrollDirection < 0){
            mapView.modifyZoom(1.2, mouseX, mouseY);
        }

    }



}