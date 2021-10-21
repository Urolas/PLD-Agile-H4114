package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.Graphics2D;
import java.awt.Graphics;
import javax.swing.JPanel;

import model.CityMap;
import model.Distribution;
import model.Request;
import model.Road;

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
    private final int VIEW_HEIGHT = 700;
    private final int VIEW_WIDTH = 700;

    /**
     * Default constructor
     */
    public MapView(CityMap cityMap, Window window) {
        super();
        this.cityMap = cityMap;
        cityMap.addObserver(this); // this observes cityMap
        cityMap.getDistribution().addObserver(this); // this observes distribution
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
        for (Road r : cityMap.getRoads()){
            displayRoad(r,Color.white, 1);
        }
        Distribution d = cityMap.getDistribution();
        if (d!=null) {
            System.out.println();
            displayDepot();
            for (Request q : d.getRequests()){
                displayRequest(q);
            }
        }
    }

    /**
     *  Method called by
     * @param r
     */
    public void displayRoad(Road r, Color c, int thickness){
        int x1 = (int)((r.getOrigin().getLongitude() - originLong) * scaleWidth);
        int y1 = -(int)((r.getOrigin().getLatitude() - originLat) * scaleHeight); /* Le repère de latitude est inversé */
        int x2 = (int)((r.getDestination().getLongitude() - originLong) * scaleWidth);
        int y2 = -(int)((r.getDestination().getLatitude() - originLat) * scaleHeight);
        g.setColor(c);
        g2.setStroke(new BasicStroke(thickness));
        g2.draw(new Line2D.Float(x1, y1, x2, y2));

    }

    public void displayRequest(Request q){
        int x1 = (int)((q.getPickup().getIntersection().getLongitude()- originLong) * scaleWidth);
        int y1 = -(int)((q.getPickup().getIntersection().getLatitude()- originLat) * scaleHeight);
        int x2 = (int)((q.getDelivery().getIntersection().getLongitude()- originLong) * scaleWidth);
        int y2 = -(int)((q.getDelivery().getIntersection().getLatitude()- originLat) * scaleHeight);
        Color c = new Color((int)(Math.random() * 0x1000000));
        g.setColor(c);
        g.fillOval(x1-5, y1-5, 10, 10);
        g.setColor(c);
        g.fillPolygon(new int[] {x2, x2+10, x2+5}, new int[] {y2, y2, y2+10}, 3);
        System.out.println("x1 =" +x1 + " y1= " + y1);
        System.out.println("x2 =" +x2 + " y2= " + y2);
    }

    public void displayDepot(){
        if (cityMap.getDistribution().getDepot().getIntersection() != null) {
            int x = (int) ((cityMap.getDistribution().getDepot().getIntersection().getLongitude() - originLong) * scaleWidth);
            int y = -(int) ((cityMap.getDistribution().getDepot().getIntersection().getLatitude() - originLat) * scaleHeight);
            g.setColor(Color.black);
            g.fillRect(x-10, y-10, 10, 10);
            System.out.println("x ="+x + " y= " + y);
        }
    }
}