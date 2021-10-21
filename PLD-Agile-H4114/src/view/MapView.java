package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;

import model.*;

import observer.Observable;
import observer.Observer;


/**
 * @author 4IF-4114
 */
public class MapView extends JPanel implements Observer {
    private Graphics g;
    private Graphics2D g2 ;
    private CityMap cityMap;
    private double scaleWidth;
    private double scaleHeight;
    private double originLat;
    private double originLong;
    private final int VIEW_HEIGHT = 800;
    private final int VIEW_WIDTH = 800;
    private final int POINT_SIZE = 15;

    /**
     * Default constructor
     */
    public MapView(CityMap cityMap, Window window) {
        super();
        this.cityMap = cityMap;
        cityMap.addObserver(this); // this observes cityMap
        cityMap.getDistribution().addObserver(this); // this observes distribution
        cityMap.getTour().addObserver(this);
        scaleWidth = 1;
        scaleHeight = 1;
        originLong = 0;
        originLat = 0;
        setLayout(null);
        setBackground(Color.LIGHT_GRAY);
        setSize(VIEW_WIDTH,VIEW_HEIGHT);
        window.getContentPane().add(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        scaleWidth = VIEW_WIDTH/cityMap.getWidth();
        scaleHeight = VIEW_HEIGHT/cityMap.getHeight();
        originLong = cityMap.getWestPoint();
        originLat = cityMap.getNordPoint();

        repaint();
    }

    public int getViewHeight() {
        return VIEW_HEIGHT;
    }

    public int getViewWidth() {
        return VIEW_WIDTH;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.g = g;
        this.g2 = (Graphics2D) g;
        for(Map.Entry<AbstractMap.SimpleEntry<String,String>,Road> road : cityMap.getRoads().entrySet()){
            displayRoad(road.getValue(),Color.white, 1,false);
        }

        Distribution d = cityMap.getDistribution();
        if (d!=null) {
            System.out.println();
            displayDepot();
            for (Request q : d.getRequests()){
                displayRequest(q);
            }
        }
        Tour t = cityMap.getTour();
        if (t!=null) {
            System.out.println();
            for (Path p : t.getPaths()){
                displayPath(p);
            }
        }
    }

    /**
     *  Method called by
     * @param r
     */
    public void displayRoad(Road r, Color c , int thickness, boolean displayTour){
        int x1 = (int)((r.getOrigin().getLongitude() - originLong) * scaleWidth);
        int y1 = -(int)((r.getOrigin().getLatitude() - originLat) * scaleHeight); /* Le repère de latitude est inversé */
        int x2 = (int)((r.getDestination().getLongitude() - originLong) * scaleWidth);
        int y2 = -(int)((r.getDestination().getLatitude() - originLat) * scaleHeight);
        if (!displayTour){
            if (r.getName().contains("Boulevard") || r.getName().contains("Avenue") || r.getName().contains("Cours") ){
                thickness=1;
                c=Color.BLACK;
            } else if (r.getName().contains("Impasse") ){
                thickness=1;
                c=Color.BLACK;
            }else {
                thickness=1;
                c=Color.BLACK;
            }
        }
        g.setColor(c);
        g2.setStroke(new BasicStroke(thickness));
        g2.draw(new Line2D.Float(x1, y1, x2, y2));

    }
    public void displayPath(Path p){
        for (Road r : p.getRoads()){
            displayRoad(r,Color.red,3,true);
        }
    }
    public void displayRequest(Request q){
        int x1 = (int)((q.getPickup().getIntersection().getLongitude()- originLong) * scaleWidth);
        int y1 = -(int)((q.getPickup().getIntersection().getLatitude()- originLat) * scaleHeight);
        int x2 = (int)((q.getDelivery().getIntersection().getLongitude()- originLong) * scaleWidth);
        int y2 = -(int)((q.getDelivery().getIntersection().getLatitude()- originLat) * scaleHeight);
        Color c = new Color((int)(Math.random() * 0x1000000));
        g.setColor(c);
        g.fillOval(x1-POINT_SIZE/2, y1-POINT_SIZE/2, POINT_SIZE, POINT_SIZE);
        g.setColor(c);
        g.fillPolygon(new int[] {x2, x2+POINT_SIZE, x2+POINT_SIZE/2}, new int[] {y2, y2, y2+POINT_SIZE}, 3);
        System.out.println("x1 =" +x1 + " y1= " + y1);
        System.out.println("x2 =" +x2 + " y2= " + y2);
    }

    public void displayDepot(){
        if (cityMap.getDistribution().getDepot().getIntersection() != null) {
            int x = (int) ((cityMap.getDistribution().getDepot().getIntersection().getLongitude() - originLong) * scaleWidth);
            int y = -(int) ((cityMap.getDistribution().getDepot().getIntersection().getLatitude() - originLat) * scaleHeight);
            g.setColor(Color.black);
            g.fillRect(x-POINT_SIZE/2, y-POINT_SIZE/2, POINT_SIZE, POINT_SIZE);
            System.out.println("x ="+x + " y= " + y);
        }
    }
}